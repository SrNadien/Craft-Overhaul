package nadiendev.craftoverhaul;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.util.Tuple;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;

import nadiendev.craftoverhaul.datagen.ModRecipeProvider;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

@Mod("craft_overhaul")
public class CraftOverhaulMod {
	public static final Logger LOGGER = LogManager.getLogger(CraftOverhaulMod.class);
	public static final String MODID = "craft_overhaul";

	public CraftOverhaulMod(IEventBus modEventBus) {
		LOGGER.info("Initializing Craft Overhaul Mod");
		LOGGER.info("Recipes will be registered via Data Generation");
		
		NeoForge.EVENT_BUS.register(this);
		
		// Registrar el listener para networking
		modEventBus.addListener(this::registerNetworking);
		
		// Registrar el listener para data generation
		modEventBus.addListener(this::gatherData);
		
		LOGGER.info("Craft Overhaul Mod initialized successfully");
	}

	/**
	 * Evento para generar datos (recetas, loot tables, etc.)
	 * NO lleva @SubscribeEvent porque ya está registrado con modEventBus.addListener
	 */
	public void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		// Registrar el proveedor de recetas
		generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookupProvider));
		
		// Registrar el proveedor de logros
		generator.addProvider(event.includeServer(), 
			new net.neoforged.neoforge.common.data.AdvancementProvider(
				output, 
				lookupProvider,
				existingFileHelper,
				java.util.List.of(new nadiendev.craftoverhaul.datagen.ModAdvancementsProvider())
			)
		);
		
		LOGGER.info("Data generators registered successfully");
	}

	// ============================================
	// NETWORKING (código original)
	// ============================================
	
	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T extends CustomPacketPayload>(
		StreamCodec<? extends FriendlyByteBuf, T> reader, 
		IPayloadHandler<T> handler
	) {}

	public static <T extends CustomPacketPayload> void addNetworkMessage(
		CustomPacketPayload.Type<T> id, 
		StreamCodec<? extends FriendlyByteBuf, T> reader, 
		IPayloadHandler<T> handler
	) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(id, new NetworkMessage<>(reader, handler));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(MODID);
		MESSAGES.forEach((id, networkMessage) -> 
			registrar.playBidirectional(
				id, 
				((NetworkMessage) networkMessage).reader(), 
				((NetworkMessage) networkMessage).handler()
			)
		);
		networkingRegistered = true;
	}

	// ============================================
	// SERVER TICK (código original)
	// ============================================
	
	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new Tuple<>(action, tick));
	}

	@SubscribeEvent
	public void tick(ServerTickEvent.Post event) {
		List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setB(work.getB() - 1);
			if (work.getB() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getA().run());
		workQueue.removeAll(actions);
	}
}