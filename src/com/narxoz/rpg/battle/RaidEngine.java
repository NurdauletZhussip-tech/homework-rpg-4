package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);
    private static final int MAX_ROUNDS = 999;

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        // TODO: Validate inputs (null checks, alive checks, required skills).
        // TODO: Implement round-based simulation:
        // 1) Team A casts on Team B
        // 2) Team B casts on Team A (if still alive)
        // 3) Track rounds and log each step
        // 4) Stop when one team is defeated (or max rounds reached)
        //
        // Optional extension:
        // Use random for critical strikes or other deterministic events.
        // Example: boolean critA = random.nextInt(100) < 10;
        if(teamA==null || teamB==null || teamASkill==null || teamBSkill==null){
            throw new IllegalArgumentException("cannot be null");
        }
        RaidResult result = new RaidResult();
        int round = 0;

        result.addLine("battle start");
        result.addLine("Team a:" + teamA.getName());
        result.addLine("Team b:" + teamB.getName());

        while(teamA.isAlive()&&teamB.isAlive()&&round<MAX_ROUNDS){
            round++;
            result.addLine("\n--- ROUND " + round + " ---");

            result.addLine(teamA.getName()+" casts "+teamASkill.getSkillName()+
                    " ("+teamASkill.getEffectName()+") on "+teamB.getName());
            teamASkill.cast(teamB);
            if(!teamB.isAlive()){
                result.addLine(teamB.getName() + "defeated");
                break;
            }

            result.addLine(teamB.getName() + " casts " + teamBSkill.getSkillName() +
                    " (" + teamBSkill.getEffectName() + ") on " + teamA.getName());
            teamBSkill.cast(teamA);
            result.addLine("Status: " + teamA.getName() + " HP=" + teamA.getHealth() +
                    ", " + teamB.getName() + " HP=" + teamB.getHealth());
        }
        result.setRounds(round);
        if(!teamA.isAlive() && !teamB.isAlive()){
            result.setWinner("Draw");
            result.addLine("\nThe battle ended in a draw!");
        }else if(!teamA.isAlive()){
            result.setWinner(teamB.getName());
            result.addLine("\n" + teamB.getName() + " wins!");
        }else if(!teamB.isAlive()){
            result.setWinner(teamA.getName());
            result.addLine("\n" + teamA.getName() + " wins!");
        }else{
            result.setWinner("rounds reached");
            result.addLine("\nwithout a winner");
        }
        return result;
    }
}
