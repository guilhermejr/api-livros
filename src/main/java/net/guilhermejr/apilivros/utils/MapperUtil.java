package net.guilhermejr.apilivros.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class MapperUtil {
	
	protected ModelMapper modelMapper;

    public MapperUtil() {
        this.modelMapper = new ModelMapper();		
    }
    
    public <S, T> T mapObject(S source, Class<T> targetClass) {
        return this.modelMapper.map(source, targetClass);
    }
    
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
          .stream()
          .map(element -> modelMapper.map(element, targetClass))
          .collect(Collectors.toList());
    }
    
    public <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
    	List<T> list = this.mapList(source.getContent(), targetClass);
    	return new PageImpl<>(list, source.getPageable(), source.getTotalElements());
    }

}
