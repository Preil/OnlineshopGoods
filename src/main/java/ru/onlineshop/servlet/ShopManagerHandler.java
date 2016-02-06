package com.mentat.onlineshop.web.servlet;

import java.util.HashSet;
import java.util.Set;

import com.mentat.onlineshop.domain.ShopManager;

public class ShopManagerHandler {
	private static Set<ShopManager> activeShopManagers = new HashSet<ShopManager>();

	public static ShopManager getShopManagerById(int shopmanagerId) {
		for (ShopManager shopManager : activeShopManagers) {
			if (shopManager.getId() == shopmanagerId) {
				return shopManager;
			}
		}
		return null;
	}

	public static Set<ShopManager> getActiveShopManagers() {
		return activeShopManagers;
	}

}
