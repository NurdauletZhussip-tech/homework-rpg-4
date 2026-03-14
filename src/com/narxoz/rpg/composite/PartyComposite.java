package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        // TODO: Composite aggregation
        // Return total health of all children (and nested children).
        int totalHealth=0;
        for(CombateNode child:children){ totalHealth+=child.getHealth();}
        return totalHealth;
    }

    @Override
    public int getAttackPower() {
        // TODO: Composite aggregation
        // Return total attack of alive children only.
        int totalAttack=0;
        for (CombatNode child:children){
            if (child.isAlive()) {totalAttack += child.getAttackPower();}
        }
        return totalAttack;
    }

    @Override
    public void takeDamage(int amount) {
        // TODO: Composite distribution
        // Distribute incoming damage across alive children.
        // Suggested baseline:
        // 1) Collect alive children
        // 2) Split amount evenly (or using your own documented rule)
        // 3) Apply damage to each child
        List<CombatNode> aliveChildren = getAliveChildren();
        if(aliveChildren.isEmpty())return;

        int damagePerChild=amount/aliveChildren.size();
        int remainder=amount%aliveChildren.size();

        for (int i=0;i<aliveChildren.size();i++) {
            int damage=damagePerChild+(i<remainder?1:0);
            aliveChildren.get(i).takeDamage(damage);
        }
    }

    @Override
    public boolean isAlive() {
        // TODO: Composite liveness
        // Return true when at least one child is alive.
        for(CombatNode child:children){
            if(child.isAlive()){return true;}
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        // TODO: Tree visualization
        // Print this node and recurse into children with increased indent.
        int totalHealth=getHealth();
        int totalAttack=getAttackPower();
        System.out.println(indent + "+ " + name + " [HP=" + totalHealth + ", ATK=" + totalAttack + "]");
        for (CombatNode child:children) {
            child.printTree(indent + "  ");}
    }

    private List<CombatNode> getAliveChildren() {
        // TODO: helper for takeDamage()
        List<CombatNode> alive = new ArrayList<>();
        for (CombatNode child : children) {
            if(child.isAlive()){alive.add(child);}
        }
        return alive;
    }
}
