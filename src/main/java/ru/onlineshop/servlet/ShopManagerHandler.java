package ru.onlineshop.servlet;

import ru.onlineshop.domain.ShopManager;
import java.util.HashSet;
import java.util.Set;


public class ShopManagerHandler {
	private static Set<ShopManager> activeShopManagers = new HashSet<>();

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
