package lip.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lip.model.Platform;

@Converter(autoApply = true)
public class PlatformConverter implements AttributeConverter<Platform, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Platform platform) {
		return platform == null ? null : platform.getId();
	}

	@Override
	public Platform convertToEntityAttribute(Integer id) {
		return Platform.valueOf(id);
	}

}
