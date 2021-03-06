/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Jul 8, 2015, 4:32:34 PM (GMT)]
 */
package vazkii.botania.common.block.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.nbt.NBTTagCompound;

public class TileCocoon extends TileMod {

	private static final String TAG_TIME_PASSED = "timePassed";

	public static final int TOTAL_TIME = 2400;

	public int timePassed;

	@Override
	public void updateEntity() {
		timePassed++;
		if(timePassed >= TOTAL_TIME)
			hatch();
	}

	public void hatch() {
		if(!worldObj.isRemote) {
			worldObj.playAuxSFX(2001, xCoord, yCoord, zCoord, Block.getIdFromBlock(getBlockType()));
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);

			EntityAnimal entity = null;
			
			float specialChance = 0.05F;
			if(Math.random() < specialChance) {
				int entityType = worldObj.rand.nextInt(3);
				switch(entityType) {
				case 0:
					entity = new EntityHorse(worldObj);
					break;
				case 1:
					entity = new EntityWolf(worldObj);
					break;
				case 2:
					entity = new EntityOcelot(worldObj);
					break;
				}
			} else {
				int entityType = worldObj.rand.nextInt(4);
				switch(entityType) {
				case 0:
					entity = new EntitySheep(worldObj);
					break;
				case 1:
					if(Math.random() < 0.01)
						entity = new EntityMooshroom(worldObj);
					else entity = new EntityCow(worldObj);
					break;
				case 2:
					entity = new EntityPig(worldObj);
					break;
				case 3:
					entity = new EntityChicken(worldObj);
					break;
				}
			}
			
			if(entity != null) {
				entity.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
				entity.setGrowingAge(-24000);
				worldObj.spawnEntityInWorld(entity);
				entity.spawnExplosionParticle();
			}
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound cmp) {
		cmp.setInteger(TAG_TIME_PASSED, timePassed);
	}

	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		timePassed = cmp.getInteger(TAG_TIME_PASSED);
	}

}
