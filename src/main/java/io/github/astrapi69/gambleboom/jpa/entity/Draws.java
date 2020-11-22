package io.github.astrapi69.gambleboom.jpa.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.identifiable.Identifiable;
import de.alpharogroup.db.entity.verifiable.Verifiable;
import de.alpharogroup.sign.annotation.SignatureExclude;
import io.github.astrapi69.gambleboom.jpa.listener.DrawsSignatureListener;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = Draws.TABLE_NAME)
@EntityListeners({ DrawsSignatureListener.class })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Draws implements Verifiable
{
	static final String SINGULAR_ENTITY_NAME = "draw";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_LOTTERY_NUMBER = "lottery_number";
	static final String COLLECTION_TABLE_NAME = "draws_lottery_numbers";
	static final String JOIN_COLUMN_NAME = SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE
		+ Identifiable.COLUMN_NAME_ID;
	static final String COLLECTION_TABLE_FOREIGN_KEY = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ TABLE_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	@Id
	@SignatureExclude
	@GeneratedValue(generator = "UUID")
	@Column(name = Identifiable.COLUMN_NAME_ID, updatable = false, nullable = false)
	private UUID id;

	/**
	 * The drawn date of this drawn numbers.
	 */
	@Column
	LocalDateTime drawnDate;

	/**
	 * The set with the drawn lottery numbers.
	 */
	@ElementCollection
	@CollectionTable(name = COLLECTION_TABLE_NAME,
		joinColumns = @JoinColumn(name = JOIN_COLUMN_NAME,
			foreignKey = @ForeignKey(name = COLLECTION_TABLE_FOREIGN_KEY)))
	@Column(name = COLUMN_NAME_LOTTERY_NUMBER)
	Set<Integer> lotteryNumbers;

	@SignatureExclude
	@Column
	String signature;

}
