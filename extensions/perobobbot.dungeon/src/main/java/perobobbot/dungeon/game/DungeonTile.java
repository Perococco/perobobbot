package perobobbot.dungeon.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;
import org.apache.logging.log4j.util.PropertySource;
import perobobbot.rendering.Animation;
import perobobbot.rendering.tile.Tile;
import perobobbot.rendering.tile.TileBasedAnimation;
import perobobbot.rendering.tile.TileGeometry;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum DungeonTile implements Tile {
    WALL_TOP_LEFT(16, 0, 16, 16),
    WALL_TOP_MID(32, 0, 16, 16),
    WALL_TOP_RIGHT(48, 0, 16, 16),
    WALL_LEFT(16, 16, 16, 16),
    WALL_MID(32, 16, 16, 16),
    WALL_RIGHT(48, 16, 16, 16),
    WALL_FOUNTAIN_TOP(64, 0, 16, 16),
    WALL_FOUNTAIN_MID_RED_ANIM_0(64, 16, 16, 16),
    WALL_FOUNTAIN_MID_RED_ANIM_1(80, 16, 16, 16),
    WALL_FOUNTAIN_MID_RED_ANIM_2(96, 16, 16, 16),
    WALL_FOUNTAIN_BASIN_RED_ANIM_0(64, 32, 16, 16),
    WALL_FOUNTAIN_BASIN_RED_ANIM_1(80, 32, 16, 16),
    WALL_FOUNTAIN_BASIN_RED_ANIM_2(96, 32, 16, 16),
    WALL_FOUNTAIN_MID_BLUE_ANIM_0(64, 48, 16, 16),
    WALL_FOUNTAIN_MID_BLUE_ANIM_1(80, 48, 16, 16),
    WALL_FOUNTAIN_MID_BLUE_ANIM_2(96, 48, 16, 16),
    WALL_FOUNTAIN_BASIN_BLUE_ANIM_0(64, 64, 16, 16),
    WALL_FOUNTAIN_BASIN_BLUE_ANIM_1(80, 64, 16, 16),
    WALL_FOUNTAIN_BASIN_BLUE_ANIM_2(96, 64, 16, 16),
    WALL_HOLE_1(48, 32, 16, 16),
    WALL_HOLE_2(48, 48, 16, 16),
    WALL_BANNER_RED(16, 32, 16, 16),
    WALL_BANNER_BLUE(32, 32, 16, 16),
    WALL_BANNER_GREEN(16, 48, 16, 16),
    WALL_BANNER_YELLOW(32, 48, 16, 16),
    COLUMN_TOP(80, 80, 16, 16),
    COLUMN_MID(80, 96, 16, 16),
    COLUMN_BASE(80, 112, 16, 16),
    WALL_COLUMN_TOP(96, 80, 16, 16),
    WALL_COLUMN_MID(96, 96, 16, 16),
    WALL_COULMN_BASE(96, 112, 16, 16),
    WALL_GOO(64, 80, 16, 16),
    WALL_GOO_BASE(64, 96, 16, 16),
    FLOOR_1(16, 64, 16, 16),
    FLOOR_2(32, 64, 16, 16),
    FLOOR_3(48, 64, 16, 16),
    FLOOR_4(16, 80, 16, 16),
    FLOOR_5(32, 80, 16, 16),
    FLOOR_6(48, 80, 16, 16),
    FLOOR_7(16, 96, 16, 16),
    FLOOR_8(32, 96, 16, 16),
    FLOOR_LADDER(48, 96, 16, 16),
    FLOOR_SPIKES_ANIM_0(16, 176, 16, 16),
    FLOOR_SPIKES_ANIM_1(32, 176, 16, 16),
    FLOOR_SPIKES_ANIM_2(48, 176, 16, 16),
    FLOOR_SPIKES_ANIM_3(64, 176, 16, 16),
    WALL_SIDE_TOP_LEFT(0, 112, 16, 16),
    WALL_SIDE_TOP_RIGHT(16, 112, 16, 16),
    WALL_SIDE_MID_LEFT(0, 128, 16, 16),
    WALL_SIDE_MID_RIGHT(16, 128, 16, 16),
    WALL_SIDE_FRONT_LEFT(0, 144, 16, 16),
    WALL_SIDE_FRONT_RIGHT(16, 144, 16, 16),
    WALL_CORNER_TOP_LEFT(32, 112, 16, 16),
    WALL_CORNER_TOP_RIGHT(48, 112, 16, 16),
    WALL_CORNER_LEFT(32, 128, 16, 16),
    WALL_CORNER_RIGHT(48, 128, 16, 16),
    WALL_CORNER_BOTTOM_LEFT(32, 144, 16, 16),
    WALL_CORNER_BOTTOM_RIGHT(48, 144, 16, 16),
    WALL_CORNER_FRONT_LEFT(32, 160, 16, 16),
    WALL_CORNER_FRONT_RIGHT(48, 160, 16, 16),
    WALL_INNER_CORNER_L_TOP_LEFT(80, 128, 16, 16),
    WALL_INNER_CORNER_L_TOP_RIGHT(64, 128, 16, 16),
    WALL_INNER_CORNER_MID_LEFT(80, 144, 16, 16),
    WALL_INNER_CORNER_MID_RIGTH(64, 144, 16, 16),
    WALL_INNER_CORNER_T_TOP_LEFT(80, 160, 16, 16),
    WALL_INNER_CORNER_T_TOP_RIGHT(64, 160, 16, 16),
    EDGE(96, 128, 16, 16),
    HOLE(96, 144, 16, 16),
    DOORS_ALL(16, 221, 64, 35),
    DOORS_FRAME_LEFT(16, 224, 16, 32),
    DOORS_FRAME_TOP(32, 221, 32, 3),
    DOORS_FRAME_RIGH(63, 224, 16, 32),
    DOORS_LEAF_CLOSED(32, 224, 32, 32),
    DOORS_LEAF_OPEN(80, 224, 32, 32),
    CHEST_EMPTY_OPEN_ANIM_0(304, 288, 16, 16),
    CHEST_EMPTY_OPEN_ANIM_1(320, 288, 16, 16),
    CHEST_EMPTY_OPEN_ANIM_2(336, 288, 16, 16),
    CHEST_FULL_OPEN_ANIM_0(304, 304, 16, 16),
    CHEST_FULL_OPEN_ANIM_1(320, 304, 16, 16),
    CHEST_FULL_OPEN_ANIM_2(336, 304, 16, 16),
    CHEST_MIMIC_OPEN_ANIM_0(304, 320, 16, 16),
    CHEST_MIMIC_OPEN_ANIM_1(320, 320, 16, 16),
    CHEST_MIMIC_OPEN_ANIM_2(336, 320, 16, 16),
    FLASK_BIG_RED(288, 224, 16, 16),
    FLASK_BIG_BLUE(304, 224, 16, 16),
    FLASK_BIG_GREEN(320, 224, 16, 16),
    FLASK_BIG_YELLOW(336, 224, 16, 16),
    FLASK_RED(288, 240, 16, 16),
    FLASK_BLUE(304, 240, 16, 16),
    FLASK_GREEN(320, 240, 16, 16),
    FLASK_YELLOW(336, 240, 16, 16),
    SKULL(288, 320, 16, 16),
    CRATE(288, 298, 16, 22),
    COIN_ANIM_0(288, 272, 8, 8),
    COIN_ANIM_1(296, 272, 8, 8),
    COIN_ANIM_2(304, 272, 8, 8),
    COIN_ANIM_3(312, 272, 8, 8),
    UI_HEART_FULL(288, 256, 16, 16),
    UI_HEART_HALF(304, 256, 16, 16),
    UI_HEART_EMPTY(320, 256, 16, 16),
    WEAPON_KNIFE(293, 18, 6, 13),
    WEAPON_RUSTY_SWORD(307, 26, 10, 21),
    WEAPON_REGULAR_SWORD(323, 26, 10, 21),
    WEAPON_RED_GEM_SWORD(339, 26, 10, 21),
    WEAPON_BIG_HAMMER(291, 42, 10, 37),
    WEAPON_HAMMER(307, 55, 10, 24),
    WEAPON_BATON_WITH_SPIKES(323, 57, 10, 22),
    WEAPON_MACE(339, 55, 10, 24),
    WEAPON_KATANA(293, 82, 6, 29),
    WEAPON_SAW_SWORD(307, 86, 10, 25),
    WEAPON_ANIME_SWORD(322, 81, 12, 30),
    WEAPON_AXE(341, 90, 9, 21),
    WEAPON_MACHETE(294, 121, 5, 22),
    WEAPON_CLEAVER(310, 124, 8, 19),
    WEAPON_DUEL_SWORD(325, 113, 9, 30),
    WEAPON_KNIGHT_SWORD(339, 114, 10, 29),
    WEAPON_GOLDEN_SWORD(291, 153, 10, 22),
    WEAPON_LAVISH_SWORD(307, 145, 10, 30),
    WEAPON_RED_MAGIC_STAFF(324, 145, 8, 30),
    WEAPON_GREEN_MAGIC_STAFF(340, 145, 8, 30),
    WEAPON_SPEAR(293, 177, 6, 30),
    TINY_ZOMBIE_IDLE_ANIM_0(368, 16, 16, 16),
    TINY_ZOMBIE_IDLE_ANIM_1(384, 16, 16, 16),
    TINY_ZOMBIE_IDLE_ANIM_2(400, 16, 16, 16),
    TINY_ZOMBIE_IDLE_ANIM_3(416, 16, 16, 16),
    TINY_ZOMBIE_RUN_ANIM_0(432, 16, 16, 16),
    TINY_ZOMBIE_RUN_ANIM_1(448, 16, 16, 16),
    TINY_ZOMBIE_RUN_ANIM_2(464, 16, 16, 16),
    TINY_ZOMBIE_RUN_ANIM_3(480, 16, 16, 16),
    GOBLIN_IDLE_ANIM_0(368, 32, 16, 16),
    GOBLIN_IDLE_ANIM_1(384, 32, 16, 16),
    GOBLIN_IDLE_ANIM_2(400, 32, 16, 16),
    GOBLIN_IDLE_ANIM_3(416, 32, 16, 16),
    GOBLIN_RUN_ANIM_0(432, 32, 16, 16),
    GOBLIN_RUN_ANIM_1(448, 32, 16, 16),
    GOBLIN_RUN_ANIM_2(464, 32, 16, 16),
    GOBLIN_RUN_ANIM_3(480, 32, 16, 16),
    IMP_IDLE_ANIM_0(368, 48, 16, 16),
    IMP_IDLE_ANIM_1(384, 48, 16, 16),
    IMP_IDLE_ANIM_2(400, 48, 16, 16),
    IMP_IDLE_ANIM_3(416, 48, 16, 16),
    IMP_RUN_ANIM_0(432, 48, 16, 16),
    IMP_RUN_ANIM_1(448, 48, 16, 16),
    IMP_RUN_ANIM_2(464, 48, 16, 16),
    IMP_RUN_ANIM_3(480, 48, 16, 16),
    SKELET_IDLE_ANIM_0(368, 80, 16, 16),
    SKELET_IDLE_ANIM_1(384, 80, 16, 16),
    SKELET_IDLE_ANIM_2(400, 80, 16, 16),
    SKELET_IDLE_ANIM_3(416, 80, 16, 16),
    SKELET_RUN_ANIM_0(432, 80, 16, 16),
    SKELET_RUN_ANIM_1(448, 80, 16, 16),
    SKELET_RUN_ANIM_2(464, 80, 16, 16),
    SKELET_RUN_ANIM_3(480, 80, 16, 16),
    MUDDY_IDLE_ANIM_0(368, 112, 16, 16),
    MUDDY_IDLE_ANIM_1(384, 112, 16, 16),
    MUDDY_IDLE_ANIM_2(400, 112, 16, 16),
    MUDDY_IDLE_ANIM_3(416, 112, 16, 16),
    MUDDY_RUN_ANIM_0(368, 112, 16, 16),
    MUDDY_RUN_ANIM_1(384, 112, 16, 16),
    MUDDY_RUN_ANIM_2(400, 112, 16, 16),
    MUDDY_RUN_ANIM_3(416, 112, 16, 16),
    SWAMPY_IDLE_ANIM_0(432, 112, 16, 16),
    SWAMPY_IDLE_ANIM_1(448, 112, 16, 16),
    SWAMPY_IDLE_ANIM_2(464, 112, 16, 16),
    SWAMPY_IDLE_ANIM_3(480, 112, 16, 16),
    SWAMPY_RUN_ANIM_0(432, 112, 16, 16),
    SWAMPY_RUN_ANIM_1(448, 112, 16, 16),
    SWAMPY_RUN_ANIM_2(464, 112, 16, 16),
    SWAMPY_RUN_ANIM_3(480, 112, 16, 16),
    ZOMBIE_IDLE_ANIM_0(368, 144, 16, 16),
    ZOMBIE_IDLE_ANIM_1(384, 144, 16, 16),
    ZOMBIE_IDLE_ANIM_2(400, 144, 16, 16),
    ZOMBIE_IDLE_ANIM_3(416, 144, 16, 16),
    ZOMBIE_RUN_ANIM_0(368, 144, 16, 16),
    ZOMBIE_RUN_ANIM_1(384, 144, 16, 16),
    ZOMBIE_RUN_ANIM_2(400, 144, 16, 16),
    ZOMBIE_RUN_ANIM_3(416, 144, 16, 16),
    ICE_ZOMBIE_IDLE_ANIM_0(432, 144, 16, 16),
    ICE_ZOMBIE_IDLE_ANIM_1(448, 144, 16, 16),
    ICE_ZOMBIE_IDLE_ANIM_2(464, 144, 16, 16),
    ICE_ZOMBIE_IDLE_ANIM_3(480, 144, 16, 16),
    ICE_ZOMBIE_RUN_ANIM_0(432, 144, 16, 16),
    ICE_ZOMBIE_RUN_ANIM_1(448, 144, 16, 16),
    ICE_ZOMBIE_RUN_ANIM_2(464, 144, 16, 16),
    ICE_ZOMBIE_RUN_ANIM_3(480, 144, 16, 16),
    MASKED_ORC_IDLE_ANIM_0(368, 172, 16, 20),
    MASKED_ORC_IDLE_ANIM_1(384, 172, 16, 20),
    MASKED_ORC_IDLE_ANIM_2(400, 172, 16, 20),
    MASKED_ORC_IDLE_ANIM_3(416, 172, 16, 20),
    MASKED_ORC_RUN_ANIM_0(432, 172, 16, 20),
    MASKED_ORC_RUN_ANIM_1(448, 172, 16, 20),
    MASKED_ORC_RUN_ANIM_2(464, 172, 16, 20),
    MASKED_ORC_RUN_ANIM_3(480, 172, 16, 20),
    ORC_WARRIOR_IDLE_ANIM_0(368, 204, 16, 20),
    ORC_WARRIOR_IDLE_ANIM_1(384, 204, 16, 20),
    ORC_WARRIOR_IDLE_ANIM_2(400, 204, 16, 20),
    ORC_WARRIOR_IDLE_ANIM_3(416, 204, 16, 20),
    ORC_WARRIOR_RUN_ANIM_0(432, 204, 16, 20),
    ORC_WARRIOR_RUN_ANIM_1(448, 204, 16, 20),
    ORC_WARRIOR_RUN_ANIM_2(464, 204, 16, 20),
    ORC_WARRIOR_RUN_ANIM_3(480, 204, 16, 20),
    ORC_SHAMAN_IDLE_ANIM_0(368, 236, 16, 20),
    ORC_SHAMAN_IDLE_ANIM_1(384, 236, 16, 20),
    ORC_SHAMAN_IDLE_ANIM_2(400, 236, 16, 20),
    ORC_SHAMAN_IDLE_ANIM_3(416, 236, 16, 20),
    ORC_SHAMAN_RUN_ANIM_0(432, 236, 16, 20),
    ORC_SHAMAN_RUN_ANIM_1(448, 236, 16, 20),
    ORC_SHAMAN_RUN_ANIM_2(464, 236, 16, 20),
    ORC_SHAMAN_RUN_ANIM_3(480, 236, 16, 20),
    NECROMANCER_IDLE_ANIM_0(368, 268, 16, 20),
    NECROMANCER_IDLE_ANIM_1(384, 268, 16, 20),
    NECROMANCER_IDLE_ANIM_2(400, 268, 16, 20),
    NECROMANCER_IDLE_ANIM_3(416, 268, 16, 20),
    NECROMANCER_RUN_ANIM_0(368, 268, 16, 20),
    NECROMANCER_RUN_ANIM_1(384, 268, 16, 20),
    NECROMANCER_RUN_ANIM_2(400, 268, 16, 20),
    NECROMANCER_RUN_ANIM_3(416, 268, 16, 20),
    WOGOL_IDLE_ANIM_0(368, 300, 16, 20),
    WOGOL_IDLE_ANIM_1(384, 300, 16, 20),
    WOGOL_IDLE_ANIM_2(400, 300, 16, 20),
    WOGOL_IDLE_ANIM_3(416, 300, 16, 20),
    WOGOL_RUN_ANIM_0(432, 300, 16, 20),
    WOGOL_RUN_ANIM_1(448, 300, 16, 20),
    WOGOL_RUN_ANIM_2(464, 300, 16, 20),
    WOGOL_RUN_ANIM_3(480, 300, 16, 20),
    CHORT_IDLE_ANIM_0(368, 328, 16, 24),
    CHORT_IDLE_ANIM_1(384, 328, 16, 24),
    CHORT_IDLE_ANIM_2(400, 328, 16, 24),
    CHORT_IDLE_ANIM_3(416, 328, 16, 24),
    CHORT_RUN_ANIM_0(432, 328, 16, 24),
    CHORT_RUN_ANIM_1(448, 328, 16, 24),
    CHORT_RUN_ANIM_2(464, 328, 16, 24),
    CHORT_RUN_ANIM_3(480, 328, 16, 24),
    BIG_ZOMBIE_IDLE_ANIM_0(16, 270, 32, 34),
    BIG_ZOMBIE_IDLE_ANIM_1(48, 270, 32, 34),
    BIG_ZOMBIE_IDLE_ANIM_2(80, 270, 32, 34),
    BIG_ZOMBIE_IDLE_ANIM_3(112, 270, 32, 34),
    BIG_ZOMBIE_RUN_ANIM_0(144, 270, 32, 34),
    BIG_ZOMBIE_RUN_ANIM_1(176, 270, 32, 34),
    BIG_ZOMBIE_RUN_ANIM_2(208, 270, 32, 34),
    BIG_ZOMBIE_RUN_ANIM_3(240, 270, 32, 34),
    OGRE_IDLE_ANIM_0(16, 320, 32, 32),
    OGRE_IDLE_ANIM_1(48, 320, 32, 32),
    OGRE_IDLE_ANIM_2(80, 320, 32, 32),
    OGRE_IDLE_ANIM_3(112, 320, 32, 32),
    OGRE_RUN_ANIM_0(144, 320, 32, 32),
    OGRE_RUN_ANIM_1(176, 320, 32, 32),
    OGRE_RUN_ANIM_2(208, 320, 32, 32),
    OGRE_RUN_ANIM_3(240, 320, 32, 32),
    BIG_DEMON_RUN_ANIM_0(144, 364, 32, 36),
    BIG_DEMON_RUN_ANIM_1(176, 364, 32, 36),
    BIG_DEMON_RUN_ANIM_2(208, 364, 32, 36),
    BIG_DEMON_RUN_ANIM_3(240, 364, 32, 36),
    ELF_F_IDLE_ANIM_0(128, 4, 16, 28),
    ELF_F_IDLE_ANIM_1(144, 4, 16, 28),
    ELF_F_IDLE_ANIM_2(160, 4, 16, 28),
    ELF_F_IDLE_ANIM_3(176, 4, 16, 28),
    ELF_F_RUN_ANIM_0(192, 4, 16, 28),
    ELF_F_RUN_ANIM_1(208, 4, 16, 28),
    ELF_F_RUN_ANIM_2(224, 4, 16, 28),
    ELF_F_RUN_ANIM_3(240, 4, 16, 28),
    ELF_F_HIT_ANIM_0(256, 4, 16, 28),
    ELF_M_IDLE_ANIM_0(128, 36, 16, 28),
    ELF_M_IDLE_ANIM_1(144, 36, 16, 28),
    ELF_M_IDLE_ANIM_2(160, 36, 16, 28),
    ELF_M_IDLE_ANIM_3(176, 36, 16, 28),
    ELF_M_RUN_ANIM_0(192, 36, 16, 28),
    ELF_M_RUN_ANIM_1(208, 36, 16, 28),
    ELF_M_RUN_ANIM_2(224, 36, 16, 28),
    ELF_M_RUN_ANIM_3(240, 36, 16, 28),
    ELF_M_HIT_ANIM_0(256, 36, 16, 28),
    KNIGHT_F_IDLE_ANIM_0(128, 68, 16, 28),
    KNIGHT_F_IDLE_ANIM_1(144, 68, 16, 28),
    KNIGHT_F_IDLE_ANIM_2(160, 68, 16, 28),
    KNIGHT_F_IDLE_ANIM_3(176, 68, 16, 28),
    KNIGHT_F_RUN_ANIM_0(192, 68, 16, 28),
    KNIGHT_F_RUN_ANIM_1(208, 68, 16, 28),
    KNIGHT_F_RUN_ANIM_2(224, 68, 16, 28),
    KNIGHT_F_RUN_ANIM_3(240, 68, 16, 28),
    KNIGHT_F_HIT_ANIM_0(256, 68, 16, 28),
    KNIGHT_M_IDLE_ANIM_0(128, 100, 16, 28),
    KNIGHT_M_IDLE_ANIM_1(144, 100, 16, 28),
    KNIGHT_M_IDLE_ANIM_2(160, 100, 16, 28),
    KNIGHT_M_IDLE_ANIM_3(176, 100, 16, 28),
    KNIGHT_M_RUN_ANIM_0(192, 100, 16, 28),
    KNIGHT_M_RUN_ANIM_1(208, 100, 16, 28),
    KNIGHT_M_RUN_ANIM_2(224, 100, 16, 28),
    KNIGHT_M_RUN_ANIM_3(240, 100, 16, 28),
    KNIGHT_M_HIT_ANIM_0(256, 100, 16, 28),
    WIZZARD_F_IDLE_ANIM_0(128, 132, 16, 28),
    WIZZARD_F_IDLE_ANIM_1(144, 132, 16, 28),
    WIZZARD_F_IDLE_ANIM_2(160, 132, 16, 28),
    WIZZARD_F_IDLE_ANIM_3(176, 132, 16, 28),
    WIZZARD_F_RUN_ANIM_0(192, 132, 16, 28),
    WIZZARD_F_RUN_ANIM_1(208, 132, 16, 28),
    WIZZARD_F_RUN_ANIM_2(224, 132, 16, 28),
    WIZZARD_F_RUN_ANIM_3(240, 132, 16, 28),
    WIZZARD_F_HIT_ANIM_0(256, 132, 16, 28),
    WIZZARD_M_IDLE_ANIM_0(128, 164, 16, 28),
    WIZZARD_M_IDLE_ANIM_1(144, 164, 16, 28),
    WIZZARD_M_IDLE_ANIM_2(160, 164, 16, 28),
    WIZZARD_M_IDLE_ANIM_3(176, 164, 16, 28),
    WIZZARD_M_RUN_ANIM_0(192, 164, 16, 28),
    WIZZARD_M_RUN_ANIM_1(208, 164, 16, 28),
    WIZZARD_M_RUN_ANIM_2(224, 164, 16, 28),
    WIZZARD_M_RUN_ANIM_3(240, 164, 16, 28),
    WIZZARD_M_HIT_ANIM_0(256, 164, 16, 28),
    LIZARD_F_IDLE_ANIM_0(128, 196, 16, 28),
    LIZARD_F_IDLE_ANIM_1(144, 196, 16, 28),
    LIZARD_F_IDLE_ANIM_2(160, 196, 16, 28),
    LIZARD_F_IDLE_ANIM_3(176, 196, 16, 28),
    LIZARD_F_RUN_ANIM_0(192, 196, 16, 28),
    LIZARD_F_RUN_ANIM_1(208, 196, 16, 28),
    LIZARD_F_RUN_ANIM_2(224, 196, 16, 28),
    LIZARD_F_RUN_ANIM_3(240, 196, 16, 28),
    LIZARD_F_HIT_ANIM_0(256, 196, 16, 28),
    LIZARD_M_IDLE_ANIM_0(128, 228, 16, 28),
    LIZARD_M_IDLE_ANIM_1(144, 228, 16, 28),
    LIZARD_M_IDLE_ANIM_2(160, 228, 16, 28),
    LIZARD_M_IDLE_ANIM_3(176, 228, 16, 28),
    LIZARD_M_RUN_ANIM_0(192, 228, 16, 28),
    LIZARD_M_RUN_ANIM_1(208, 228, 16, 28),
    LIZARD_M_RUN_ANIM_2(224, 228, 16, 28),
    LIZARD_M_RUN_ANIM_3(240, 228, 16, 28),
    LIZARD_M_HIT_ANIM_0(256, 228, 16, 28),
    ;

    @Delegate
    private final Tile delegate;

    DungeonTile(int x, int y, int width, int height) {
        this.delegate = DungeonTileSet.INSTANCE.extractTile(new TileGeometry(x, y, width, height));
    }

    public static @NonNull Tile pickOneFloor(@NonNull Random random) {
        return Holder.FLOORS[random.nextInt(Holder.FLOORS.length)];
    }

    public static @NonNull Animation animationContaining(@NonNull DungeonTile dungeonTile) {
        return TileInfo.create(dungeonTile)
                       .map(TileInfo::getPrefix)
                       .map(Holder.ANIMATIONS::get)
                       .orElseThrow(() -> new RuntimeException("Tile '" + dungeonTile + "' is not part of an animation"));
    }

    private static class Holder {
        private static final ImmutableMap<String, Animation> ANIMATIONS;

        private static final Tile[] FLOORS = {FLOOR_1, FLOOR_2, FLOOR_3, FLOOR_4, FLOOR_5, FLOOR_6, FLOOR_7, FLOOR_8};

        static {
            final var map = Arrays.stream(values())
                                  .map(TileInfo::create)
                                  .flatMap(Optional::stream)
                                  .collect(Collectors.groupingBy(TileInfo::getPrefix,
                                                                 Collectors.toList()));

            final Function<List<TileInfo>, Animation> animationBuilder = l -> l.stream()
                                                                               .sorted(TileInfo.BY_PREFIX_AND_INDEX)
                                                                               .collect(Collectors.collectingAndThen(
                                                                                       Collectors.mapping(TileInfo::getDungeonTile, ImmutableList.toImmutableList()),
                                                                                       TileBasedAnimation::new)
                                                                               );

            ANIMATIONS = map.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, e -> animationBuilder.apply(e.getValue())));
        }


    }

    @Value
    private static class TileInfo {

        public static final Comparator<TileInfo> BY_PREFIX_AND_INDEX = Comparator.comparing(TileInfo::getPrefix).thenComparing(TileInfo::getIndex);

        public static final String ANIM_TAG = "_ANIM_";

        public static @NonNull Optional<TileInfo> create(@NonNull DungeonTile dungeonTile) {
            final var name = dungeonTile.name();
            final var idx = name.indexOf(ANIM_TAG);
            if (idx < 0) {
                return Optional.empty();
            }
            final var prefix = name.substring(0, idx);
            final var index = Integer.parseInt(name.substring(idx + ANIM_TAG.length()));

            return Optional.of(new TileInfo(dungeonTile, prefix, index));
        }

        @NonNull DungeonTile dungeonTile;

        @NonNull String prefix;

        int index;

    }

}
