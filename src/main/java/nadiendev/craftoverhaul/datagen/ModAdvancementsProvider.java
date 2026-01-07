package nadiendev.craftoverhaul.datagen;

import nadiendev.craftoverhaul.CraftOverhaulMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
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
                    Items.BEDROCK,
                    Component.translatable("advancement.craftoverhaul.six_seven.title"),
                    Component.translatable("advancement.craftoverhaul.six_seven.description"),
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
                    Component.translatable("advancement.craftoverhaul.a_laburar.title"),
                    Component.translatable("advancement.craftoverhaul.a_laburar.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("has_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "a_laburar").toString());

            // diamantes para ti
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.DIAMOND,
                    Component.translatable("advancement.craftoverhaul.diamante_para_ti.title"),
                    Component.translatable("advancement.craftoverhaul.diamante_para_ti.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("threw_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(
                    ItemPredicate.Builder.item().of(Items.DIAMOND).build()
                ))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "diamante_para_ti").toString());

            //alto asado 
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.CAMPFIRE,
                    Component.translatable("advancement.craftoverhaul.alto_asado.title"),
                    Component.translatable("advancement.craftoverhaul.alto_asado.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("has_campfire", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CAMPFIRE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "alto_asado").toString());

            // alto guiso
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.FURNACE,
                    Component.translatable("advancement.craftoverhaul.alto_guiso.title"),
                    Component.translatable("advancement.craftoverhaul.alto_guiso.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("has_furnace", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FURNACE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "alto_guiso").toString());

            //peluche
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.LAPIS_LAZULI,
                    Component.translatable("advancement.craftoverhaul.peluche.title"),
                    Component.translatable("advancement.craftoverhaul.peluche.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("has_lapis", InventoryChangeTrigger.TriggerInstance.hasItems(Items.LAPIS_LAZULI))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "peluche").toString());
            
            //esmarelda
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.EMERALD,
                    Component.translatable("advancement.craftoverhaul.esmarelda.title"),
                    Component.translatable("advancement.craftoverhaul.esmarelda.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("has_emerald", InventoryChangeTrigger.TriggerInstance.hasItems(Items.EMERALD))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "esmarelda").toString());

            //Preston
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.REDSTONE,
                    Component.translatable("advancement.craftoverhaul.preston.title"),
                    Component.translatable("advancement.craftoverhaul.preston.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat
                    false  // hidden
                )
                .addCriterion("has_redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "preston").toString());
            
            // oro españolito
            Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                    Items.GOLD_INGOT,
                    Component.translatable("advancement.craftoverhaul.espanolito.title"),
                    Component.translatable("advancement.craftoverhaul.espanolito.description"),
                    null,
                    FrameType.TASK,
                    true,  // showToast
                    true,  // announceChat  
                    false  // hidden
                )
                .addCriterion("has_gold_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "espanolito").toString());

            CraftOverhaulMod.LOGGER.info("Logros Generados Correctamente -_-");
        }
    }
}