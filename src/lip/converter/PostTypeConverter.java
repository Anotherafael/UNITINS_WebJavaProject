package lip.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lip.model.PostType;

@Converter(autoApply = true)
public class PostTypeConverter implements AttributeConverter<PostType, Integer>{

	@Override
	public Integer convertToDatabaseColumn(PostType postType) {
		return postType == null ? null : postType.getId();
	}

	@Override
	public PostType convertToEntityAttribute(Integer id) {
		return PostType.valueOf(id);
	}
}
