package nadiendev.craftoverhaul.datagen;

import nadiendev.craftoverhaul.CraftOverhaulMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput writer) {
        // Wrapper para evitar generar advancements automáticos
        RecipeOutput recipeOutput = new RecipeOutput() {
            @Override
            public void accept(ResourceLocation id, net.minecraft.world.item.crafting.Recipe<?> recipe, 
                             net.minecraft.advancements.AdvancementHolder advancement) {
                // Solo guardamos la receta, ignoramos el advancement
                writer.accept(id, recipe, null);
            }
            
            @Override
            public net.minecraft.advancements.Advancement.Builder advancement() {
                return writer.advancement();
            }

            @Override
            public void accept(ResourceLocation id, net.minecraft.world.item.crafting.Recipe<?> recipe, 
                             net.minecraft.advancements.AdvancementHolder advancement, ICondition... conditions) {
                // Solo guardamos la receta, ignoramos el advancement
                writer.accept(id, recipe, null, conditions);
            }
        };
        
        // ==========================================
        // RECETAS SHAPELESS
        // ==========================================
        
        // Polvo de blaze desde ojo de ender
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLAZE_POWDER, 8)
            .requires(Items.ENDER_EYE)
            .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "blaze_powder_from_eye_of_ender"));

        // Polvo de blaze desde varita de blaze
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLAZE_POWDER, 16)
            .requires(Items.BLAZE_ROD)
            .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "blaze_powder_from_blaze_rod"));

        // ==========================================
        // RECETAS DE HORNO
        // ==========================================
        
        // Cuero desde carne podrida (horno)
        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(Items.ROTTEN_FLESH),
            RecipeCategory.MISC,
            Items.LEATHER,
            1.0f,
            200
        )
        .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_smelting"));
        
        // Cuero desde carne podrida (alto horno)
        SimpleCookingRecipeBuilder.blasting(
            Ingredient.of(Items.ROTTEN_FLESH),
            RecipeCategory.MISC,
            Items.LEATHER,
            1.0f,
            100
        )
        .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_blasting"));

        // Cuero desde carne podrida (ahumador)
        SimpleCookingRecipeBuilder.smoking(
            Ingredient.of(Items.ROTTEN_FLESH),
            RecipeCategory.MISC,
            Items.LEATHER,
            1.0f,
            100
        )
        .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_smoking"));

        // Cuero desde carne podrida (fogata)
        SimpleCookingRecipeBuilder.campfireCooking(
            Ingredient.of(Items.ROTTEN_FLESH),
            RecipeCategory.MISC,
            Items.LEATHER,
            1.0f,
            600
        )
        .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "leather_from_rotten_flesh_campfire"));

        // Perla de ender desde ojo de ender
        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(Items.ENDER_EYE),
            RecipeCategory.MISC,
            Items.ENDER_PEARL,
            0.5f,
            200
        )
        .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "ender_pearl_from_eye_of_ender_smelting"));

        // ==========================================
        // RECETAS CRAFTEABLES ESPECIALES
        // ==========================================

        // Elytra crafteable
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.ELYTRA, 1)
            .pattern("aba")
            .pattern("cdc")
            .pattern("cec")
            .define('a', Items.DIAMOND)
            .define('b', Items.STRING)
            .define('c', Items.PHANTOM_MEMBRANE)
            .define('d', Items.NETHER_STAR)
            .define('e', Items.DIAMOND_BLOCK)
            .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "elytra_from_diamonds_phantom_membrane_and_nether_star"));

        // Mob Spawner crafteable
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.SPAWNER, 1)
            .pattern("aba")
            .pattern("bcb")
            .pattern("aba")
            .define('a', Items.CHAIN)
            .define('b', Items.NETHER_STAR)
            .define('c', Items.NETHERITE_BLOCK)
            .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "mob_spawner_from_nether_star_and_chain"));

        // Cama desde alfombras
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.LIME_BED, 1)
            .pattern("aaa")
            .pattern("bbb")
            .define('a', Items.LIME_CARPET)
            .define('b', ItemTags.PLANKS)
            .unlockedBy("has_carpet", has(Items.LIME_CARPET))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "carpet_bed"));

        // Cofres desde troncos
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.CHEST, 8)
            .pattern("aaa")
            .pattern("a a")
            .pattern("aaa")
            .define('a', ItemTags.LOGS)
            .unlockedBy("has_logs", has(ItemTags.LOGS))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chest_from_logs"));

        // Ladrillos desde bloque de ladrillos
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.BRICK, 4)
            .requires(Items.BRICKS)
            .unlockedBy("has_bricks", has(Items.BRICKS))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "bricks_from_brick_block"));

        // Montura de caballo crafteable
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SADDLE, 1)
            .pattern("aaa")
            .pattern("aba")
            .pattern("b b")
            .define('a', Items.LEATHER)
            .define('b', Items.IRON_INGOT)
            .unlockedBy("has_leather", has(Items.LEATHER))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "saddle_from_leather_and_iron"));

        // Lingote de netherite
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHERITE_BLOCK, 4)
            .pattern("aaa")
            .pattern("abb")
            .pattern("bb ")
            .define('a', Items.GOLD_BLOCK)
            .define('b', Items.NETHERITE_SCRAP)
            .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "netherite_block_recipeuwu"));

        // Mejora de herrería de netherita
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 4)
            .pattern("aba")
            .pattern("aca")
            .pattern("aaa")
            .define('a', Items.NETHER_BRICK)
            .define('b', Items.ENDER_EYE)
            .define('c', Items.DIAMOND)
            .unlockedBy("has_diamond", has(Items.DIAMOND))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "smithing_netherite_upgrade"));

        // ==========================================
        // ARMADURA DE COTA DE MALLA
        // ==========================================

        // Casco
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_HELMET, 1)
            .pattern("aaa")
            .pattern("a a")
            .define('a', Items.CHAIN)
            .unlockedBy("has_chain", has(Items.CHAIN))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_helmet"));

        // Pechera
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE, 1)
            .pattern("a a")
            .pattern("aaa")
            .pattern("aaa")
            .define('a', Items.CHAIN)
            .unlockedBy("has_chain", has(Items.CHAIN))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_chestplate"));

        // Pantalón
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_LEGGINGS, 1)
            .pattern("aaa")
            .pattern("a a")
            .pattern("a a")
            .define('a', Items.CHAIN)
            .unlockedBy("has_chain", has(Items.CHAIN))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_leggings"));

        // Botas
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_BOOTS, 1)
            .pattern("a a")
            .pattern("a a")
            .define('a', Items.CHAIN)
            .unlockedBy("has_chain", has(Items.CHAIN))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "chainmail_boots"));

        // ==========================================
        // OTROS ITEMS
        // ==========================================

        // Manzana de Notch
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE, 1)
            .pattern("aaa")
            .pattern("aba")
            .pattern("aaa")
            .define('a', Items.GOLD_BLOCK)
            .define('b', Items.APPLE)
            .unlockedBy("has_gold_block", has(Items.GOLD_BLOCK))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "enchanted_golden_apple_from_gold_blocks"));

        // Magnetita
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.LODESTONE, 1)
            .pattern("aaa")
            .pattern("aba")
            .pattern("aaa")
            .define('a', Items.CHISELED_STONE_BRICKS)
            .define('b', Items.COPPER_INGOT)
            .unlockedBy("has_copper", has(Items.COPPER_INGOT))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "magnetite_balanced"));

        // Arcilla desde bloque de arcilla
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CLAY_BALL, 4)
            .requires(Items.CLAY)
            .unlockedBy("has_clay", has(Items.CLAY))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "clay_from_clay_block"));

        // Portal al End crafteable
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.END_PORTAL_FRAME, 1)
            .pattern("aaa")
            .pattern("bcb")
            .pattern("bbb")
            .define('a', Items.NETHERITE_INGOT)
            .define('b', Items.DIAMOND_BLOCK)
            .define('c', Items.ENDER_EYE)
            .unlockedBy("has_netherite", has(Items.NETHERITE_INGOT))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "end_portal_frame_from_netherite"));

        // Telaraña desde cuerda
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.COBWEB, 1)
            .pattern("aaa")
            .pattern("aaa")
            .pattern("aaa")
            .define('a', Items.STRING)
            .unlockedBy("has_string", has(Items.STRING))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "cobweb_from_string"));

       // Palos desde troncos
ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STICK, 16)
    .pattern("a  ")
    .pattern("a  ")
    .pattern("   ")
    .define('a', ItemTags.LOGS)
    .unlockedBy("has_logs", has(ItemTags.LOGS))
    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "stick_from_logs"));

// Hilo desde lana
ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING, 16)
    .pattern("   ")
    .pattern(" a ")
    .pattern("   ")
    .define('a', ItemTags.WOOL)
    .unlockedBy("has_wool", has(ItemTags.WOOL))
    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "string_from_wool"));

      // Hilo desde telaraña
     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING, 9)
       .pattern("   ")
       .pattern(" a ")
       .pattern("   ")
       .define('a', Items.COBWEB)
       .unlockedBy("has_cobweb", has(Items.COBWEB))
       .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "string_from_cobweb_shaped"));
        // Semillas de cacao
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COCOA_BEANS, 3)
            .requires(Items.INK_SAC)
            .requires(Items.RED_DYE)
            .requires(Items.YELLOW_DYE)
            .unlockedBy("has_dye", has(Items.RED_DYE))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "cocoa_beans_from_dyes"));

        // Núcleo pesado
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.HEAVY_CORE, 1)
            .pattern("aaa")
            .pattern("aba")
            .pattern("aaa")
            .define('a', Items.OBSIDIAN)
            .define('b', Items.NETHERITE_SCRAP)
            .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "heavy_core"));
        // campana crafteable 
         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BELL, 1)
         .pattern("ccc")
         .pattern("bab")
         .pattern("b b")
         .define('a', Items.GOLD_BLOCK)
         .define('b', Items.STONE)
         .define('c', ItemTags.LOGS)
         .unlockedBy("has_gold_block", has(Items.GOLD_BLOCK))
         .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "bell_recipe"));

// obsidiana llorosa crafteo crying_obsidian craftable
ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.CRYING_OBSIDIAN, 1)
    .pattern(" o ")
    .pattern("oao")
    .pattern(" o ")
    .define('a', Items.OBSIDIAN)
    .define('o', Items.WATER_BUCKET)
    .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "crying_obsidian_recipe"));

// totem de inmortalidad crafteable
// patron de receta para que la extendion de visual studio code la autocomple con tabulador
//   [aba] a: esmeralda c:bloque de oro b:lingote de oro
//   [bcb]
//   [ b ]
ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TOTEM_OF_UNDYING, 1)
    .pattern("aba")
    .pattern("bcb")
    .pattern(" b ")
    .define('a', Items.EMERALD)
    .define('b', Items.GOLD_INGOT)
    .define('c', Items.GOLD_BLOCK)
    .unlockedBy("has_emerald", has(Items.EMERALD))
    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "totem_of_undying_recipe"));


    //name tag crafteable
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NAME_TAG, 4)
            .pattern("   ")
            .pattern(" a ")
            .pattern("b  ")
            .define('a', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "nuggets")))
            .define('b', Items.PAPER)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "name_tag"));
    
     //trial key craft
     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TRIAL_KEY, 2)
            .pattern("aaa")
            .pattern("bcb")
            .pattern("bbb")
            .define('a', Items.POLISHED_TUFF)
            .define('b', Items.COPPER_BLOCK)
            .define('c', Items.TOTEM_OF_UNDYING)
            .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "trial_key"));

    // aminous trial key craft
     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.OMINOUS_TRIAL_KEY, 2)
            .pattern("aaa")
            .pattern("bcb")
            .pattern("bbb")
            .define('a', Items.POLISHED_TUFF)
            .define('b', Items.OXIDIZED_COPPER)
            .define('c', Items.TOTEM_OF_UNDYING)
            .unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "ominous_trial_key"));

    //nether star craft
     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHER_STAR, 1)
            .pattern("aaa")
            .pattern("bbb")
            .pattern(" b ")
            .define('a', Items.WITHER_SKELETON_SKULL)
            .define('b', Items.SOUL_SAND)
            .unlockedBy("has_wither_skeleton_skull", has(Items.WITHER_SKELETON_SKULL))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CraftOverhaulMod.MODID, "nether_star_recipe_uwu"));


    }
}