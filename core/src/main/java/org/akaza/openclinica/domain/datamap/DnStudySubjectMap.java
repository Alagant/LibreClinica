/*
 * LibreClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).

 * For details see: https://libreclinica.org/license
 * LibreClinica, copyright (C) 2020
 */
package org.akaza.openclinica.domain.datamap;

// default package
// Generated Aug 8, 2013 11:32:37 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.akaza.openclinica.domain.DataMapDomainObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * DnStudySubjectMap generated by hbm2java
 */
@Entity
@Table(name = "dn_study_subject_map")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DnStudySubjectMap extends DataMapDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2974425156631941758L;
	private DnStudySubjectMapId dnStudySubjectMapId;
	private StudySubject studySubject;
	private DiscrepancyNote discrepancyNote;

	public DnStudySubjectMap() {
	}

	public DnStudySubjectMap(DnStudySubjectMapId id) {
		this.dnStudySubjectMapId = id;
	}

	public DnStudySubjectMap(DnStudySubjectMapId id, StudySubject studySubject,
			DiscrepancyNote discrepancyNote) {
		this.dnStudySubjectMapId = id;
		this.studySubject = studySubject;
		this.discrepancyNote = discrepancyNote;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "studySubjectId", column = @Column(name = "study_subject_id")),
			@AttributeOverride(name = "discrepancyNoteId", column = @Column(name = "discrepancy_note_id")),
			@AttributeOverride(name = "columnName", column = @Column(name = "column_name")) })
	public DnStudySubjectMapId getDnStudySubjectMapId() {
		return this.dnStudySubjectMapId;
	}

	public void setDnStudySubjectMapId(DnStudySubjectMapId id) {
		this.dnStudySubjectMapId = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "study_subject_id", insertable = false, updatable = false)
	public StudySubject getStudySubject() {
		return this.studySubject;
	}

	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discrepancy_note_id", insertable = false, updatable = false)
	public DiscrepancyNote getDiscrepancyNote() {
		return this.discrepancyNote;
	}

	public void setDiscrepancyNote(DiscrepancyNote discrepancyNote) {
		this.discrepancyNote = discrepancyNote;
	}

	

}
