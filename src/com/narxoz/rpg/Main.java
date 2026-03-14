package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.*;
import com.narxoz.rpg.composite.CombatNode;
import com.narxoz.rpg.composite.EnemyUnit;
import com.narxoz.rpg.composite.HeroUnit;
import com.narxoz.rpg.composite.PartyComposite;
import com.narxoz.rpg.composite.RaidGroup;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        // TODO: build leaves
        HeroUnit warrior = new HeroUnit("Platon", 140, 30);
        HeroUnit mage = new HeroUnit("Sokrat", 90, 40);
        EnemyUnit goblin = new EnemyUnit("Aristotel", 70, 20);
        EnemyUnit orc = new EnemyUnit("Kant", 120, 25);
        EnemyUnit ogr = new EnemyUnit("Zeus", 150, 55);

        // TODO: build composite hierarchy (nested)
        PartyComposite heroes = new PartyComposite("Heroes");
        heroes.add(warrior);
        heroes.add(mage);

        PartyComposite frontline = new PartyComposite("Frontline");
        frontline.add(goblin);
        frontline.add(orc);

        PartyComposite backline = new PartyComposite("Backline");
        backline.add(ogr);

        RaidGroup enemies = new RaidGroup("Enemy Raid");
        enemies.add(frontline);
        enemies.add(backline);
        System.out.println("--- Team Structures ---");
        heroes.printTree("");
        enemies.printTree("");

        System.out.println("same skill with different effects(bridge pattern)");

        // TODO: Bridge combinations
        Skill slashFire = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill slashIce = new SingleTargetSkill("Slash", 20, new IceEffect());
        Skill stormFire = new AreaSkill("Storm", 15, new FireEffect());
        Skill slashShadow = new SingleTargetSkill("Slash", 20, new ShadowEffect());
        Skill massiveShadow=new AreaSkill("Storm",99,new ShadowEffect());

        System.out.println("   " + slashFire.getSkillName() + " + " + slashFire.getEffectName());
        System.out.println("   " + slashIce.getSkillName() + " + " + slashIce.getEffectName());
        System.out.println("   " + slashShadow.getSkillName() + " + " + slashShadow.getEffectName());

        System.out.println("\n2. Different skills sharing same effect:");

        System.out.println("   " + stormFire.getSkillName() + " + " + stormFire.getEffectName());
        System.out.println("   " + massiveShadow.getSkillName() + " + " + massiveShadow.getEffectName());

        // TODO: run raid
        System.out.println("battle is started");
        RaidEngine engine = new RaidEngine().setRandomSeed(42L);
        RaidResult result = engine.runRaid(heroes, enemies, slashFire, stormFire);

        System.out.println("\n--- Raid Result ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        for (String line : result.getLog()) {
            System.out.println(line);
        }
    }
}
