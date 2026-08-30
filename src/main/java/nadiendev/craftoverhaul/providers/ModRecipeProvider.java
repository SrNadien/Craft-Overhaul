package nadiendev.craftoverhaul.providers;

import nadiendev.craftoverhaul.CraftOverhaulMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    private final HolderGetter<Item> items;

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected void buildRecipes() {
        // ==========================================
        // RECETAS SHAPELESS
        // ==========================================

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.BLAZE_POWDER, 8)
            .requires(Items.ENDER_EYE)
            .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "blaze_powder_from_eye_of_ender")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.BLAZE_POWDER, 16)
            .requires(Items.BLAZE_ROD)
            .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "blaze_powder_from_blaze_rod")));

         // ==========================================
        // RECETAS DE HORNO
       // ==========================================
 
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.ROTTEN_FLESH), RecipeCategory.MISC, CookingBookCategory.MISC, Items.LEATHER, 1.0f, 200)
            .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_smelting")));
 
         // ==========================================
        // RECETAS DE ALTO HORNO
       // ==========================================
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(Items.ROTTEN_FLESH), RecipeCategory.MISC, CookingBookCategory.MISC, Items.LEATHER, 1.0f, 100)
            .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_blasting")));
 

           // ==========================================
          // RECETAS DEL AHUMADOR
         // ==========================================
        // -- SMOKING (ahumador) --
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.ROTTEN_FLESH), RecipeCategory.MISC, Items.LEATHER, 1.0f, 100)
            .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_smoking")));
 
           // ==========================================
          // RECETAS DE LA HOGUERA
         // ==========================================
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.ROTTEN_FLESH), RecipeCategory.MISC, Items.LEATHER, 1.0f, 600)
            .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_campfire")));
 
        // ==========================================
        // RECETAS SHAPED
        // ==========================================

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, Items.ELYTRA, 1)
            .pattern("aba").pattern("cdc").pattern("cec")
            .define('a', Items.DIAMOND).define('b', Items.STRING)
            .define('c', Items.PHANTOM_MEMBRANE).define('d', Items.NETHER_STAR)
            .define('e', Items.DIAMOND_BLOCK)
            .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "elytra_from_diamonds_phantom_membrane_and_nether_star")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, Items.SPAWNER, 1)
            .pattern("aba").pattern("bcb").pattern("aba")
            .define('a', Items.IRON_CHAIN).define('b', Items.NETHER_STAR)
            .define('c', Items.NETHERITE_BLOCK)
            .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "mob_spawner_from_nether_star_and_chain")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, Items.BED.pick(DyeColor.LIME), 1)
            .pattern("aaa").pattern("bbb")
            .define('a', Items.CARPET.pick(DyeColor.LIME)).define('b', ItemTags.PLANKS)
            .unlockedBy("has_carpet", has(Items.CARPET.pick(DyeColor.LIME)))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "carpet_bed")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, Items.CHEST, 8)
            .pattern("aaa").pattern("a a").pattern("aaa")
            .define('a', ItemTags.LOGS)
            .unlockedBy("has_logs", has(ItemTags.LOGS))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chest_from_logs")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, Items.BRICK, 4)
            .requires(Items.BRICKS)
            .unlockedBy("has_bricks", has(Items.BRICKS))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "bricks_from_brick_block")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.SADDLE, 1)
            .pattern("aaa").pattern("aba").pattern("b b")
            .define('a', Items.LEATHER).define('b', Items.IRON_INGOT)
            .unlockedBy("has_leather", has(Items.LEATHER))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "saddle_from_leather_and_iron")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.NETHERITE_BLOCK, 4)
            .pattern("aaa").pattern("abb").pattern("bb ")
            .define('a', Items.GOLD_BLOCK).define('b', Items.NETHERITE_SCRAP)
            .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "netherite_block_recipeuwu")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 4)
            .pattern("aba").pattern("aca").pattern("aaa")
            .define('a', Items.NETHER_BRICK).define('b', Items.ENDER_EYE)
            .define('c', Items.DIAMOND)
            .unlockedBy("has_diamond", has(Items.DIAMOND))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "smithing_netherite_upgrade")));

        // ==========================================
        // ARMADURA DE COTA DE MALLA SHAPED
        // ==========================================

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, Items.CHAINMAIL_HELMET, 1)
            .pattern("aaa").pattern("a a")
            .define('a', Items.IRON_CHAIN)
            .unlockedBy("has_chain", has(Items.IRON_CHAIN))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_helmet")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE, 1)
            .pattern("a a").pattern("aaa").pattern("aaa")
            .define('a', Items.IRON_CHAIN)
            .unlockedBy("has_chain", has(Items.IRON_CHAIN))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_chestplate")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, Items.CHAINMAIL_LEGGINGS, 1)
            .pattern("aaa").pattern("a a").pattern("a a")
            .define('a', Items.IRON_CHAIN)
            .unlockedBy("has_chain", has(Items.IRON_CHAIN))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_leggings")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, Items.CHAINMAIL_BOOTS, 1)
            .pattern("a a").pattern("a a")
            .define('a', Items.IRON_CHAIN)
            .unlockedBy("has_chain", has(Items.IRON_CHAIN))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_boots")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE, 1)
            .pattern("aaa").pattern("aba").pattern("aaa")
            .define('a', Items.GOLD_BLOCK).define('b', Items.APPLE)
            .unlockedBy("has_gold_block", has(Items.GOLD_BLOCK))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "enchanted_golden_apple_from_gold_blocks")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, Items.LODESTONE, 1)
            .pattern("aaa").pattern("aba").pattern("aaa")
            .define('a', Items.CHISELED_STONE_BRICKS).define('b', Items.COPPER_INGOT)
            .unlockedBy("has_copper", has(Items.COPPER_INGOT))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "magnetite_balanced")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.CLAY_BALL, 4)
            .requires(Items.CLAY)
            .unlockedBy("has_clay", has(Items.CLAY))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "clay_from_clay_block")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, Items.END_PORTAL_FRAME, 1)
            .pattern("aaa").pattern("bcb").pattern("bbb")
            .define('a', Items.NETHERITE_INGOT).define('b', Items.DIAMOND_BLOCK)
            .define('c', Items.ENDER_EYE)
            .unlockedBy("has_netherite", has(Items.NETHERITE_INGOT))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "end_portal_frame_from_netherite")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, Items.COBWEB, 1)
            .pattern("aaa").pattern("aaa").pattern("aaa")
            .define('a', Items.STRING)
            .unlockedBy("has_string", has(Items.STRING))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "cobweb_from_string")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.STICK, 16)
            .pattern("a  ").pattern("a  ").pattern("   ")
            .define('a', ItemTags.LOGS)
            .unlockedBy("has_logs", has(ItemTags.LOGS))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "stick_from_logs")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.STRING, 16)
            .pattern("   ").pattern(" a ").pattern("   ")
            .define('a', ItemTags.WOOL)
            .unlockedBy("has_wool", has(ItemTags.WOOL))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "string_from_wool")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.STRING, 9)
            .pattern("   ").pattern(" a ").pattern("   ")
            .define('a', Items.COBWEB)
            .unlockedBy("has_cobweb", has(Items.COBWEB))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "string_from_cobweb_shaped")));

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.COCOA_BEANS, 3)
            .requires(Items.INK_SAC).requires(Items.DYE.pick(DyeColor.RED)).requires(Items.DYE.pick(DyeColor.YELLOW))
            .unlockedBy("has_dye", has(Items.DYE.pick(DyeColor.RED)))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "cocoa_beans_from_dyes")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, Items.HEAVY_CORE, 1)
            .pattern("aaa").pattern("aba").pattern("aaa")
            .define('a', Items.OBSIDIAN).define('b', Items.NETHERITE_SCRAP)
            .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "heavy_core")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.BELL, 1)
            .pattern("ccc").pattern("bab").pattern("b b")
            .define('a', Items.GOLD_BLOCK).define('b', Items.STONE)
            .define('c', ItemTags.LOGS)
            .unlockedBy("has_gold_block", has(Items.GOLD_BLOCK))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "bell_recipe")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, Items.CRYING_OBSIDIAN, 1)
            .pattern(" o ").pattern("oao").pattern(" o ")
            .define('a', Items.OBSIDIAN).define('o', Items.WATER_BUCKET)
            .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "crying_obsidian_recipe")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.TOTEM_OF_UNDYING, 1)
            .pattern("aba").pattern("bcb").pattern(" b ")
            .define('a', Items.EMERALD).define('b', Items.GOLD_INGOT)
            .define('c', Items.GOLD_BLOCK)
            .unlockedBy("has_emerald", has(Items.EMERALD))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "totem_of_undying_recipe")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.NAME_TAG, 4)
            .pattern("   ").pattern(" a ").pattern("b  ")
            .define('a', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "nuggets")))
            .define('b', Items.PAPER)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "name_tag")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.TRIAL_KEY, 2)
            .pattern("aaa").pattern("bcb").pattern("bbb")
            .define('a', Items.POLISHED_TUFF).define('b', Items.COPPER_BLOCK.weathering().unaffected())
            .define('c', Items.TOTEM_OF_UNDYING)
            .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "trial_key")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.OMINOUS_TRIAL_KEY, 2)
            .pattern("aaa").pattern("bcb").pattern("bbb")
            .define('a', Items.POLISHED_TUFF).define('b', Items.COPPER_BLOCK.weathering().oxidized())
            .define('c', Items.TOTEM_OF_UNDYING)
            .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "ominous_trial_key")));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.NETHER_STAR, 1)
            .pattern("aaa").pattern("bbb").pattern(" b ")
            .define('a', Items.WITHER_SKELETON_SKULL).define('b', Items.SOUL_SAND)
            .unlockedBy("has_wither_skeleton_skull", has(Items.WITHER_SKELETON_SKULL))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(CraftOverhaulMod.MODID, "nether_star_recipe_uwu")));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            RecipeOutput noAdvancementOutput = new RecipeOutput() {
                @Override
                public void accept(ResourceKey<Recipe<?>> id, Recipe<?> recipe, AdvancementHolder advancement) {
                    output.accept(id, recipe, null);
                }

                @Override
                public Advancement.Builder advancement() {
                    return output.advancement();
                }

                @Override
                public void accept(ResourceKey<Recipe<?>> id, Recipe<?> recipe, AdvancementHolder advancement, ICondition... conditions) {
                    output.accept(id, recipe, null, conditions);
                }

                @Override
                public void includeRootAdvancement() {}
            };
            return new ModRecipeProvider(registries, noAdvancementOutput);
        }

        @Override
        public String getName() {
            return "CraftOverhaul Recipes";
        }
    }
}