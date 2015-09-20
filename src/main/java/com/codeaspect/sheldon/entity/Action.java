package com.codeaspect.sheldon.entity;

/**
 * Represents the change in the entity.
 *
 * @author urvaksh.rogers
 *
 */
public enum Action {
	/**
	 * A new Object was created.
	 */
	CREATE, /**
			 * The object was modified.
			 */
	MODIFY, /**
			 * The Object was deleted.
			 */
	DELETE;
}
