package com.onefin.ewallet.common.base.repository.sequenceTrans;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.onefin.ewallet.common.domain.base.sequenceTrans.NumberSequenceTrans;

public interface INumberSequenceRepository extends JpaRepository<NumberSequenceTrans, String> {

	@RestResource(exported = false)
	@Query(value = "Select n from NumberSequenceTrans n Where name = :name")
	public List<NumberSequenceTrans> findAllByName(@Param("name") String name);

	@RestResource(exported = false)
	@Query(value = "Select n from NumberSequenceTrans n Where name = :name")
	public NumberSequenceTrans findByName(@Param("name") String name);

	@Modifying
	@Transactional
	@Query(value = "Update NumberSequenceTrans Set currentNumber = currentNumber + dfltBlockSize, lastModified = CURRENT_TIMESTAMP Where name = :name")
	public void updateByName(@Param("name") String name);

	@Modifying
	@Transactional
	@Query(value = "Update NumberSequenceTrans Set currentNumber = currentNumber + :size, lastModified = CURRENT_TIMESTAMP Where name = :name")
	public void updateByNameAndSize(@Param("name") String name, @Param("size") Long size);

	@RestResource(exported = false)
	@Query(value = "Select currentNumber - (dfltBlockSize - 1) as firstNumber, currentNumber as lastNumber From NumberSequenceTrans Where name = :name")
	public List<Object[]> queryByName(@Param("name") String name);

	@RestResource(exported = false)
	@Query(value = "Select currentNumber - :size as firstNumber, currentNumber as lastNumber From NumberSequenceTrans Where name = :name")
	public List<Object[]> queryByNameAndSize(@Param("name") String name, @Param("size") Long size);

}