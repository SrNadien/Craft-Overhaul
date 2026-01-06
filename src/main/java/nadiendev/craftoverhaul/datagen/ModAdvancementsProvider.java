package nadiendev.craftoverhaul.datagen;

import nadiendev.craftoverhaul.CraftOverhaulMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class ModAdvancementsProvider implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        
        // Logro raíz (root) - "Six Seven"
        AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
            .display(
                Items.NETHER_STAR,
                Component.literal("Six Seven"),
                Component.literal("six seven six seven"),
                ResourceLocation.parse("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                AdvancementType.TASK,
                true, // showToast
                true, // announceChat
                false  // hidden
            )
            .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
<<<<<<< Updated upstream
            .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "root").toString());
=======
            .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "six_seven").toString());
>>>>>>> Stashed changes

        // Logro 1 - "A laburar -_-"
        Advancement.Builder.advancement()
            .parent(rootAdvancement)
            .display(
                Items.CRAFTING_TABLE,
                Component.literal("A laburar  -_-"),
                Component.literal("Trabaja Vago"),
                null,
                AdvancementType.TASK,
                true,  // showToast
                true,  // announceChat
                false  // hidden
            )
            .addCriterion("has_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
            .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "a_laburar").toString());

        CraftOverhaulMod.LOGGER.info("Advancements generated successfully");
    }
}