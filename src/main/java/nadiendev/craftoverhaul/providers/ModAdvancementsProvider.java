package nadiendev.craftoverhaul.providers;

import nadiendev.craftoverhaul.CraftOverhaulMod;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

import java.util.List;

public class ModAdvancementsProvider {

    public static SingleRegistryBootstrap<Advancement> create() {
        return new AdvancementProvider(List.of(Generator::new));
    }

    private static class Generator extends AdvancementSubProvider {
        Generator(BootstrapContext<Advancement> output) {
            super(output);
        }

        @Override
        public void generate() {

            AdvancementHolder root = Advancement.Builder.advancement()
                    .rootDisplay(Items.BEDROCK,
                            Component.translatable("advancement.craftoverhaul.six_seven.title"),
                            Component.translatable("advancement.craftoverhaul.six_seven.description"),
                            Identifier.parse("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                    .save(output, id("six_seven"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.CRAFTING_TABLE,
                            Component.translatable("advancement.craftoverhaul.a_laburar.title"),
                            Component.translatable("advancement.craftoverhaul.a_laburar.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_crafting_table",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                    .save(output, id("a_laburar"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.DIAMOND,
                            Component.translatable("advancement.craftoverhaul.diamante_para_ti.title"),
                            Component.translatable("advancement.craftoverhaul.diamante_para_ti.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("threw_diamond",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                    .save(output, id("diamante_para_ti"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.CAMPFIRE,
                            Component.translatable("advancement.craftoverhaul.alto_asado.title"),
                            Component.translatable("advancement.craftoverhaul.alto_asado.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_campfire",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.CAMPFIRE))
                    .save(output, id("alto_asado"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.FURNACE,
                            Component.translatable("advancement.craftoverhaul.alto_guiso.title"),
                            Component.translatable("advancement.craftoverhaul.alto_guiso.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_furnace",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.FURNACE))
                    .save(output, id("alto_guiso"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.LAPIS_LAZULI,
                            Component.translatable("advancement.craftoverhaul.peluche.title"),
                            Component.translatable("advancement.craftoverhaul.peluche.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_lapis",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.LAPIS_LAZULI))
                    .save(output, id("peluche"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.EMERALD,
                            Component.translatable("advancement.craftoverhaul.esmarelda.title"),
                            Component.translatable("advancement.craftoverhaul.esmarelda.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_emerald",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.EMERALD))
                    .save(output, id("esmarelda"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.REDSTONE,
                            Component.translatable("advancement.craftoverhaul.preston.title"),
                            Component.translatable("advancement.craftoverhaul.preston.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_redstone",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                    .save(output, id("preston"));

            Advancement.Builder.advancement().parent(root)
                    .display(Items.GOLD_INGOT,
                            Component.translatable("advancement.craftoverhaul.espanolito.title"),
                            Component.translatable("advancement.craftoverhaul.espanolito.description"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("has_gold_ingot",
                            InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                    .save(output, id("espanolito"));

            CraftOverhaulMod.LOGGER.info("Logros Generados Correctamente -_-");
        }

        private static String id(String path) {
            return Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, path).toString();
        }
    }
}
