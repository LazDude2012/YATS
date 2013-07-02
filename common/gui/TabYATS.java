package YATS.common.gui;

import YATS.common.YATS;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabYATS extends CreativeTabs
{
	public TabYATS(int id, String label)
	{
		super(id, label);
	}
	public Item getTabIconItem()
	{
		return YATS.itemSpanner;
	}
	public String getTranslatedTabLabel()
	{
		return "Yet Another Tube System";
	}
}
