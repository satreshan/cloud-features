package com.sap.cc.bulletinboard.ads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InMemoryAdvertisementStorage {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<Long, Advertisement> advertisements = new HashMap<>();

	public Advertisement saveAdvertisement(final Advertisement ad) {
		boolean isAdvertisementIdEmptyOrNonExisting = ad.getId() == null || !advertisements.containsKey(ad.getId());

		if (isAdvertisementIdEmptyOrNonExisting) {
			Long id = advertisements.size() + 1L;
			ad.setId(id);
		}

		advertisements.put(ad.getId(), ad);
		return ad;
	}

	public Optional<Advertisement> retrieveAdvertisementById(Long id) {
		if(id <= 0) {
			IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Negative ids are not allowed");
			String stacktrace = ExceptionUtils.getStackTrace(illegalArgumentException);
			logger.warn(stacktrace);
			throw illegalArgumentException;
		}
		return Optional.ofNullable(advertisements.get(id));
	}

	public List<Advertisement> retrieveAllAdvertisements() {
		ArrayList<Advertisement> returnValue = new ArrayList<Advertisement>(advertisements.values());
		return returnValue;
	}

	public void deleteAllAdvertisements() {
		advertisements.clear();
	}

	public void deleteAdvertisement(Long id) {
		advertisements.remove(id);
	}

}
