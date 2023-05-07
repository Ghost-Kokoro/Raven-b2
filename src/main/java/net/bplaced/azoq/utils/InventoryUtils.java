package net.bplaced.azoq.utils;

import com.google.common.collect.Multimap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.utils.TimeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
    private static Minecraft mc;
    private static TimeUtils time;
    
    public static int findItemInHot(final Item item) {
        for (int itemSlot = 0; itemSlot < 9; ++itemSlot) {
            final ItemStack itemStack = InventoryUtils.mc.thePlayer.inventory.mainInventory[itemSlot];
            if (itemStack != null && itemStack.getItem() == item) {
                return itemSlot;
            }
        }
        return -1;
    }
    
    private static float getItemDamage(ItemStack itemStack) {
    	Multimap multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty()) {
        	Iterator iterator = multimap.entries().iterator();
           if (iterator.hasNext()) {
        	   Entry entry = (Entry)iterator.next();
        	   AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
        	   double damage;
        	   if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
        		   damage = attributeModifier.getAmount();
        	   } else {
        		   damage = attributeModifier.getAmount() * 100.0D;
        	   }
        	   if (attributeModifier.getAmount() > 1.0D) {
        		   return (float) (1.0F + damage);
        	   }
        	   return 1.0F;
           }
        }
        return 1.0F;
    }

    
    public static int getBestWeapon(final Entity target) {
        final int originalSlot = InventoryUtils.mc.thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (byte slot = 0; slot < 9; ++slot) {
            InventoryUtils.mc.thePlayer.inventory.currentItem = slot;
            final ItemStack itemStack = InventoryUtils.mc.thePlayer.getHeldItem();
            if (itemStack != null) {
                float damage = getItemDamage(itemStack);
                damage += EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
                if (damage > weaponDamage) {
                    weaponDamage = damage;
                    weaponSlot = slot;
                }
            }
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }
    
    public static void refill(final Item i) {
        for (int slot = 9; slot <= 36; ++slot) {
            final ItemStack item = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
            if (item != null && item.getItem() == i) {
                InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, (EntityPlayer)InventoryUtils.mc.thePlayer);
                break;
            }
        }
        if (Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Auto Close").getValBoolean() && InventoryUtils.time.hasReached(Client.INSTANCE.getSettingsManager().getSettingByName("Refill:Delay").getValDouble())) {
            if (InventoryUtils.mc.currentScreen instanceof GuiInventory) {
                InventoryUtils.mc.displayGuiScreen((GuiScreen)null);
            }
            InventoryUtils.time.reset();
        }
    }
    
    static {
        InventoryUtils.mc = Minecraft.getMinecraft();
        InventoryUtils.time = new TimeUtils();
    }
}
