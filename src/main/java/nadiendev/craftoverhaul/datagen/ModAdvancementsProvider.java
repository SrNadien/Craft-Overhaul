package nadiendev.craftoverhaul.datagen;

import nadiendev.craftoverhaul.CraftOverhaulMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;

public class ModAdvancementsProvider extends ForgeAdvancementProvider {

    public ModAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancementGenerator()));
    }

    public static class ModAdvancementGenerator implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            
            // Logro raíz (root) - "Six Seven"
            Advancement rootAdvancement = Advancement.Builder.advancement()
                .display(
                    Items.NETHER_STAR,
                    Component.literal("Six Seven"),
                    Component.literal("six seven six seven"),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.TASK,
                    true, // showToast
                    true, // announceChat
                    false  // hidden
                )
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "six_seven").toString());

            // Logro 1 - "A laburar -_-"
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.CRAFTING_TABLE,
                    Component.literal("A laburar  -_-"),
                    Component.literal("Trabaja Vago"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("has_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "a_laburar").toString());

            CraftOverhaulMod.LOGGER.info("Advancements generated successfully");
        }
    }
}