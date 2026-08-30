package nadiendev.craftoverhaul.providers;

import nadiendev.craftoverhaul.CraftOverhaulMod;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementsProvider extends AdvancementProvider {

    public ModAdvancementsProvider(PackOutput output,
                                   CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new Generator()));
    }

    private static class Generator implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider registries,
                             Consumer<AdvancementHolder> saver) {

            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(Items.BEDROCK,
                            Component.translatable("advancement.craftoverhaul.six_seven.title"),
                            Component.translatable("advancement.craftoverhaul.six_seven.description"),
                            Identifier.parse("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "six_seven"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.CRAFTING_TABLE,
                            Component.translatable("advancement.craftoverhaul.a_laburar.title"),
                            Component.translatable("advancement.craftoverhaul.a_laburar.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_crafting_table",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "a_laburar"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.DIAMOND,
                            Component.translatable("advancement.craftoverhaul.diamante_para_ti.title"),
                            Component.translatable("advancement.craftoverhaul.diamante_para_ti.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("threw_diamond",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "diamante_para_ti"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.CAMPFIRE,
                            Component.translatable("advancement.craftoverhaul.alto_asado.title"),
                            Component.translatable("advancement.craftoverhaul.alto_asado.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_campfire",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.CAMPFIRE))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "alto_asado"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.FURNACE,
                            Component.translatable("advancement.craftoverhaul.alto_guiso.title"),
                            Component.translatable("advancement.craftoverhaul.alto_guiso.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_furnace",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.FURNACE))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "alto_guiso"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.LAPIS_LAZULI,
                            Component.translatable("advancement.craftoverhaul.peluche.title"),
                            Component.translatable("advancement.craftoverhaul.peluche.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_lapis",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.LAPIS_LAZULI))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "peluche"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.EMERALD,
                            Component.translatable("advancement.craftoverhaul.esmarelda.title"),
                            Component.translatable("advancement.craftoverhaul.esmarelda.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_emerald",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.EMERALD))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "esmarelda"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.REDSTONE,
                            Component.translatable("advancement.craftoverhaul.preston.title"),
                            Component.translatable("advancement.craftoverhaul.preston.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_redstone",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "preston"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.GOLD_INGOT,
                            Component.translatable("advancement.craftoverhaul.espanolito.title"),
                            Component.translatable("advancement.craftoverhaul.espanolito.description"),
                            null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_gold_ingot",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                    .save(saver, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "espanolito"));

            CraftOverhaulMod.LOGGER.info("Logros Generados Correctamente -_-");
        }
    }
}