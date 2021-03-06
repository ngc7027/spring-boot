/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.context.properties.source;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.core.env.MapPropertySource;
import org.springframework.util.Assert;

/**
 * An {@link ConfigurationPropertySource} backed by a {@link Map} and using standard name
 * mapping rules.
 *
 * @author Phillip Webb
 * @author Madhura Bhave
 * @since 2.0.0
 */
public class MapConfigurationPropertySource implements ConfigurationPropertySource {

	private final Map<String, Object> source;

	private final ConfigurationPropertySource delegate;

	/**
	 * Create a new empty {@link MapConfigurationPropertySource} instance.
	 */
	public MapConfigurationPropertySource() {
		this(Collections.emptyMap());
	}

	/**
	 * Create a new {@link MapConfigurationPropertySource} instance with entries copies
	 * from the specified map.
	 * @param map the source map
	 */
	public MapConfigurationPropertySource(Map<?, ?> map) {
		this.source = new LinkedHashMap<>();
		this.delegate = new PropertySourceConfigurationPropertySource(
				new MapPropertySource("source", this.source),
				new DefaultPropertyMapper());
		putAll(map);
	}

	/**
	 * Add all enties from the specified map.
	 * @param map the source map
	 */
	public void putAll(Map<?, ?> map) {
		Assert.notNull(map, "Map must not be null");
		map.forEach(this::put);
	}

	/**
	 * Add an individual entry.
	 * @param name the name
	 * @param value the value
	 */
	public void put(Object name, Object value) {
		this.source.put((name == null ? null : name.toString()), value);
	}

	@Override
	public ConfigurationProperty getConfigurationProperty(
			ConfigurationPropertyName name) {
		return this.delegate.getConfigurationProperty(name);
	}

	@Override
	public Iterator<ConfigurationPropertyName> iterator() {
		return this.delegate.iterator();
	}

	@Override
	public Stream<ConfigurationPropertyName> stream() {
		return this.delegate.stream();
	}

}
