package me.jishuna.actionconfiglib;

import java.util.UUID;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

public class TagManager {

	private static TagManager instance;

	private final Multimap<UUID, String> tagMap = LinkedHashMultimap.create();

	public boolean hasTag(UUID id, String tag) {
		return this.tagMap.containsEntry(id, tag);
	}

	public void addTag(UUID id, String tag) {
		this.tagMap.put(id, tag);
	}
	
	public void removeTag(UUID id, String tag) {
		this.tagMap.remove(id, tag);
	}

	public static TagManager getInstance() {
		if (instance == null)
			instance = new TagManager();
		return instance;
	}

}
