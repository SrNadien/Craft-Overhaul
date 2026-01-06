package nadiendev.craftoverhaul;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;

import net.minecraft.util.Tuple;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraftforge.common.data.ExistingFileHelper;

import nadiendev.craftoverhaul.datagen.ModRecipeProvider;
import nadiendev.craftoverhaul.datagen.ModAdvancementsProvider;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;

@Mod(CraftOverhaulMod.MODID)
public class CraftOverhaulMod {
	public static final Logger LOGGER = LogManager.getLogger(CraftOverhaulMod.class);
	
	public static final String MODID = "craft_overhaul";
	
	public CraftOverhaulMod(FMLJavaModLoadingContext context) {
		LOGGER.info("Initializing Craft Overhaul Mod");
		LOGGER.info("Recipes will be registered via Data Generation");
		
		// Obtener el event bus del mod desde el contexto
		IEventBus modEventBus = context.getModEventBus();
		
		// Registrar el evento de data generation
		modEventBus.addListener(this::gatherData);
		
		// Registrar eventos del forge bus
		MinecraftForge.EVENT_BUS.register(this);
		
		LOGGER.info("Craft Overhaul Mod initialized successfully");
	}

	// ============================================
	// DATA GENERATION
	// ============================================
	
	private void gatherData(GatherDataEvent event) {
		LOGGER.info("=== DATAGEN EVENT TRIGGERED ===");
		
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		LOGGER.info("Registering Recipe Provider...");
		generator.addProvider(event.includeServer(), new ModRecipeProvider(output));
		
		LOGGER.info("Registering Advancement Provider...");
		generator.addProvider(event.includeServer(), 
			new ModAdvancementsProvider(output, lookupProvider, existingFileHelper)
		);

		LOGGER.info("=== ALL DATA PROVIDERS REGISTERED ===");
	}

	// ============================================
	// NETWORKING 
	// ============================================
	
	private static boolean networkingRegistered = false;
	private static int messageID = 0;
	private static final Map<ResourceLocation, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T>(
		BiConsumer<T, FriendlyByteBuf> writer,
		Function<FriendlyByteBuf, T> reader,
		BiConsumer<T, Supplier<net.minecraftforge.network.NetworkEvent.Context>> handler
	) {}

	public static <T> void addNetworkMessage(
		Class<T> messageClass,
		BiConsumer<T, FriendlyByteBuf> encoder,
		Function<FriendlyByteBuf, T> decoder,
		BiConsumer<T, Supplier<net.minecraftforge.network.NetworkEvent.Context>> messageConsumer
	) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(ResourceLocation.fromNamespaceAndPath(MODID, messageClass.getSimpleName().toLowerCase()), 
			new NetworkMessage<>(encoder, decoder, messageConsumer));
	}

	// ============================================
	// SERVER TICK
	// ============================================
	
	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		workQueue.add(new Tuple<>(action, tick));
	}

	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
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
}