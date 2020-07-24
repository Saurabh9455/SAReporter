
package com.SuperemeAppealReporter.api.service.impl;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.bo.GetCaseListBo;
import com.SuperemeAppealReporter.api.bo.UploadCasePdfBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.CaseDao;
import com.SuperemeAppealReporter.api.io.entity.AdditionalAppellantRespondentEntity;
import com.SuperemeAppealReporter.api.io.entity.CaseEntity;
import com.SuperemeAppealReporter.api.io.entity.CaseHistoryEntity;
import com.SuperemeAppealReporter.api.io.entity.CasesRefferedEntity;
import com.SuperemeAppealReporter.api.io.entity.CitationCategoryEntity;
import com.SuperemeAppealReporter.api.io.entity.CitationEntity;
import com.SuperemeAppealReporter.api.io.entity.CourtBenchEntity;
import com.SuperemeAppealReporter.api.io.entity.CourtBranchEntity;
import com.SuperemeAppealReporter.api.io.entity.CourtDetailEntity;
import com.SuperemeAppealReporter.api.io.entity.CourtEntity;
import com.SuperemeAppealReporter.api.io.entity.DoubleCouncilDetailEntity;
import com.SuperemeAppealReporter.api.io.entity.HeadnoteEntity;
import com.SuperemeAppealReporter.api.io.entity.JournalEntity;
import com.SuperemeAppealReporter.api.io.entity.SecondaryCitationEntity;
import com.SuperemeAppealReporter.api.io.entity.SingleCouncilDetailEntity;
import com.SuperemeAppealReporter.api.io.repository.CaseRepository;
import com.SuperemeAppealReporter.api.io.repository.CitationCategoryRepository;
import com.SuperemeAppealReporter.api.io.repository.CourtBenchRepository;
import com.SuperemeAppealReporter.api.io.repository.CourtBranchRepository;
import com.SuperemeAppealReporter.api.io.repository.CourtRepository;
import com.SuperemeAppealReporter.api.io.repository.JournalReposiotry;
import com.SuperemeAppealReporter.api.service.CaseService;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.service.impl.CaseServiceImpl;
import com.SuperemeAppealReporter.api.ui.model.request.AdditionalAppellantRespondentRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CaseHistoryRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CasesRefferedRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CitationRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CourtDetailRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DoubleCouncilDetailRequest;
import com.SuperemeAppealReporter.api.ui.model.request.HeadnoteRequest;
import com.SuperemeAppealReporter.api.ui.model.request.SingleCouncilDetailRequest;
import com.SuperemeAppealReporter.api.ui.model.response.AdditionalAppellantRespondentResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CaseHistoryResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CasesReferredResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CitationCategoryResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CitationCategoryResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.CitationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CitationResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CourtBenchResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.CourtBranchResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.CourtDetailResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CourtDetailResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.CourtResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CourtResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.DoubleCouncilDetailResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetCaseListResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetCaseListResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.HeadnoteResponse;
import com.SuperemeAppealReporter.api.ui.model.response.JournalResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.SecondaryCitationResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.SingleCouncileDetailResponse;
import com.SuperemeAppealReporter.api.ui.model.response.YearResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class CaseServiceImpl implements CaseService {
	@Autowired
	private CitationCategoryRepository citationCategoryRepository;

	@Autowired
	private JournalReposiotry journalRepository;

	@Autowired
	private CourtBenchRepository courtBenchRepository;

	@Autowired
	private CourtBranchRepository courtBranchRepository;

	@Autowired
	private MasterService masterService;

	@Autowired
	private CourtRepository courtRepository;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private CaseDao caseDao;

	@Value("${file.upload-dir}")
	private String fileUploadDirectory;

	public CommonMessageResponse addCaseService(AddCaseBo addCaseBo) {
		CommonMessageResponse commonMessageResponse = null;
		try {
			
			int docId = (int) addCaseBo.getDocId();
			
			CitationRequest citationRequest = addCaseBo.getCitationRequest();
			String citationYear = citationRequest.getYear();
			String citationPageNumber = citationRequest.getPageNumber();
			String otherCitation = citationRequest.getOtherCitation();
			
			CitationCategoryEntity citationCategoryEntity = this.citationCategoryRepository
					.findById(Integer.valueOf(citationRequest.getCitationCategoryId())).get();
			JournalEntity journalEntity = this.journalRepository
					.findById(Integer.valueOf(citationRequest.getJournalId())).get();
			
			CitationEntity citationEntity = new CitationEntity();
			citationEntity.setYear(citationYear);
			citationEntity.setPageNumber(citationPageNumber);
			citationEntity.setOtherCitation(otherCitation);
			citationEntity.setJournalEntity(journalEntity);
			citationEntity.setCitationCategoryEntity(citationCategoryEntity);
			
			citationEntity.setSecondaryCitationEntity(null);
			if(journalEntity.getJournalType().equalsIgnoreCase(AppConstant.CommonConstant.JOURNAL_TYPE_SAR_ONLINE)){
				
				CitationCategoryEntity secondaryCitationCategoryEntity = this.citationCategoryRepository
						.findById(Integer.valueOf(citationRequest.getSecondaryCitationCategoryId())).get();
				JournalEntity secondaryJournalEntity = this.journalRepository
						.findById(Integer.valueOf(citationRequest.getSecondaryJournalId())).get();
				
				String secondaryCitationYear = citationRequest.getSecondaryYear();
				String secondaryCitationPageNumber = citationRequest.getSecondaryPageNumber();
			
				SecondaryCitationEntity secondaryCitationEntity = new SecondaryCitationEntity();
				secondaryCitationEntity.setCitationCategoryEntity(secondaryCitationCategoryEntity);
				secondaryCitationEntity.setPageNumber(secondaryCitationPageNumber);
				secondaryCitationEntity.setYear(secondaryCitationYear);
				secondaryCitationEntity.setJournalEntity(secondaryJournalEntity);
				
				citationEntity.setSecondaryCitationEntity(secondaryCitationEntity);
				
			}
			
		
			
			
			CourtDetailRequest courtDetailRequest = addCaseBo.getCourtDetailRequest();
			String allJudges = courtDetailRequest.getAllJudges();
			CourtBenchEntity courtBenchEntity = this.courtBenchRepository
					.findById(Integer.valueOf(courtDetailRequest.getCourtBenchId())).get();
			CourtBranchEntity courtBranchEntity = this.courtBranchRepository
					.findById(Integer.valueOf(courtDetailRequest.getCourtBranchId())).get();
			CourtEntity courtEntity = this.courtRepository.findById(Integer.valueOf(courtDetailRequest.getCourtId()))
					.get();
			CourtDetailEntity courtDetailEntity = new CourtDetailEntity();
			courtDetailEntity.setAllJudges(allJudges);
			courtDetailEntity.setCourtBenchEntity(courtBenchEntity);
			courtDetailEntity.setCourtBranchEntity(courtBranchEntity);
			courtDetailEntity.setCourtEntity(courtEntity);
			List<AdditionalAppellantRespondentEntity> additionalAppellantRespondentEntityList = new ArrayList<>();
			List<AdditionalAppellantRespondentRequest> additionalAppellantRespondentRequestList = addCaseBo
					.getAdditionalAppellantRespondentRequestList();
			if (additionalAppellantRespondentRequestList != null)
				for (AdditionalAppellantRespondentRequest req : additionalAppellantRespondentRequestList) {
					AdditionalAppellantRespondentEntity additionalAppellantRespondentEntity = new AdditionalAppellantRespondentEntity();
					additionalAppellantRespondentEntity.setCaseNumber(req.getCase_number() + "");
					additionalAppellantRespondentEntity.setExtraCaseAndYear(req.getExtraCaseAndYear());
					additionalAppellantRespondentEntity.setRespondent(req.getRespondent());
					additionalAppellantRespondentEntity.setAppellant(req.getAppellant());
					additionalAppellantRespondentEntityList.add(additionalAppellantRespondentEntity);
				}
			CaseHistoryRequest caseHistoryRequest = addCaseBo.getCaseHistoryRequest();
			String caseNumber = caseHistoryRequest.getCaseNumber();
			String year = caseHistoryRequest.getYear();
			String decidedDate = caseHistoryRequest.getDecidedDay();
			String decidedMonth = caseHistoryRequest.getDecidedMonth();
			String decidedYear = caseHistoryRequest.getDecidedYear();
			String caseHistoryNote = caseHistoryRequest.getNotes();
			CaseHistoryEntity caseHistoryEntity = new CaseHistoryEntity();
			caseHistoryEntity.setDecided_day(decidedDate);
			caseHistoryEntity.setDecidedMonth(decidedMonth);
			caseHistoryEntity.setYear(year);
			caseHistoryEntity.setNotes(caseHistoryNote);
			caseHistoryEntity.setCaseNumber(caseNumber);
			caseHistoryEntity.setDecidedYear(decidedYear);
			String remarkable = addCaseBo.getRemarkable();
			List<HeadnoteEntity> headnoteEntityList = new ArrayList<>();
			List<HeadnoteRequest> headnoteRequestList = addCaseBo.getHeadNoteRequestList();
			for (HeadnoteRequest req : headnoteRequestList) {
				HeadnoteEntity headnoteEntity = new HeadnoteEntity();
				headnoteEntity.setActname1(req.getActname1());
				headnoteEntity.setActname2(req.getActname2());
				headnoteEntity.setActname3(req.getActname3());
				headnoteEntity.setActname4(req.getActname4());
				headnoteEntity.setHeadnote(req.getHeadnote());
				headnoteEntity.setPriority(req.getPriority());
				headnoteEntity.setSection1(req.getSection1());
				headnoteEntity.setSection2(req.getSection2());
				headnoteEntity.setSection3(req.getSection3());
				headnoteEntity.setSection4(req.getSection4());
				headnoteEntity.setTopic(req.getTopic());
				headnoteEntity.setParagraph(req.getParagraph());
				/**added for update  in headnote **/
				headnoteEntity.setOrder1(req.getOrder1());
				headnoteEntity.setOrder2(req.getOrder2());
				headnoteEntity.setOrder3(req.getOrder3());
				headnoteEntity.setOrder4(req.getOrder4());
				
				headnoteEntity.setRule1(req.getRule1());
				headnoteEntity.setRule2(req.getRule2());
				headnoteEntity.setRule3(req.getRule3());
				headnoteEntity.setRule4(req.getRule4());
				
				headnoteEntity.setArticle1(req.getArticle1());
				headnoteEntity.setArticle2(req.getArticle2());
				headnoteEntity.setArticle3(req.getArticle3());
				headnoteEntity.setArticle4(req.getArticle4());
				
				
				
				headnoteEntityList.add(headnoteEntity);
			}
			List<CasesRefferedEntity> casesRefferedEntityList = new ArrayList<>();
			List<CasesRefferedRequest> casesRefferedRequestList = addCaseBo.getCasesReferredRequestList();
			for (CasesRefferedRequest req : casesRefferedRequestList) {
				CasesRefferedEntity casesRefferedEntity = new CasesRefferedEntity();
				casesRefferedEntity.setCasesReferred(req.getCasesReferred());
				casesRefferedEntity.setPartyName(req.getPartyName());
				casesRefferedEntity.setLinkedDocId(req.getLinkedDocId());
				casesRefferedEntityList.add(casesRefferedEntity);
				
			}
			SingleCouncilDetailRequest singleCouncilDetailRequest = null;
			DoubleCouncilDetailRequest doubleCouncilDetailRequest = null;
			DoubleCouncilDetailEntity detailEntity = null;
			SingleCouncilDetailEntity singleCouncilDetailEntity = null;
			if (addCaseBo.getSingleCouncilDetailRequest() != null) {
				singleCouncilDetailRequest = addCaseBo.getSingleCouncilDetailRequest();
				singleCouncilDetailEntity = new SingleCouncilDetailEntity();
				singleCouncilDetailEntity.setPetionerName(singleCouncilDetailRequest.getPetitionerName());
			} else if (addCaseBo.getDoubleCouncilDetailRequest() != null) {
				doubleCouncilDetailRequest = addCaseBo.getDoubleCouncilDetailRequest();
				detailEntity = new DoubleCouncilDetailEntity();
				detailEntity.setAdvocateForAppellant(doubleCouncilDetailRequest.getAdvocateForAppellant());
				detailEntity.setAdvocateForRespondent(doubleCouncilDetailRequest.getAdvocateForRespondent());
				detailEntity.setExtraCouncilDetails(doubleCouncilDetailRequest.getExtraCouncilDetails());
			}
			String judgeName = addCaseBo.getJudgeName();
			String judgementType = addCaseBo.getJudgementType();
			String caseResult = addCaseBo.getCaseResult();
			String judgementOrder = addCaseBo.getJudgementOrder();
			String appellant = addCaseBo.getAppellant();
			String respondent = addCaseBo.getRespondent();
			CaseEntity caseEntity = new CaseEntity();
			if (additionalAppellantRespondentEntityList.size() > 0)
				caseEntity.setAdditionalAppellantRespondentEntitySet(additionalAppellantRespondentEntityList);
			caseEntity.setAppellant(appellant);
			caseEntity.setCaseHistoryEntity(caseHistoryEntity);
			caseEntity.setCaseResult(caseResult);
			caseEntity.setCasesReferredEntitySet(casesRefferedEntityList);
			caseEntity.setCitationEntity(citationEntity);
			caseEntity.setCourtDetailEntity(courtDetailEntity);
			caseEntity.setDocId(docId);
			caseEntity.setHeadNoteEntitySet(headnoteEntityList);
			caseEntity.setJudgementOrder(judgementOrder);
			caseEntity.setJudgementType(judgementType);
			caseEntity.setJudgeName(judgeName);
			caseEntity.setOriginalPdfPath(null);
			caseEntity.setRemarkable(remarkable);
			caseEntity.setRespondent(respondent);
			
			for (HeadnoteEntity headnoteEntity : headnoteEntityList)
				headnoteEntity.setCaseEntity(caseEntity);
			for (CasesRefferedEntity casesReferredEntity : casesRefferedEntityList)
				casesReferredEntity.setCaseEntity(caseEntity);
			for (AdditionalAppellantRespondentEntity additionalAppellantRespondentEntity : additionalAppellantRespondentEntityList)
				additionalAppellantRespondentEntity.setCaseEntity(caseEntity);
			
			if (singleCouncilDetailEntity != null) {
				caseEntity.setSingleCouncilDetailEntity(singleCouncilDetailEntity);
			} else if (detailEntity != null) {
				caseEntity.setDoubleCouncilDetailEntity(detailEntity);
			}
			if (this.caseRepository.findByDocId(Long.parseLong(String.valueOf(docId))) != null)
				throw new AppException("Duplicate Case Error", "140", "The given Doc Id is already present");
			this.caseRepository.save(caseEntity);
			commonMessageResponse = new CommonMessageResponse();
			commonMessageResponse.setMsg("Case successfully created.");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> addCaseService()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonMessageResponse;
	}

	public CommonMessageResponse editCaseService(AddCaseBo addCaseBo) {
		CommonMessageResponse commonMessageResponse = null;
		try {
			
			
			int docId = (int) addCaseBo.getDocId();
			CaseEntity caseEntityExsiting = this.caseRepository.findByDocId(docId);
			String existingPath = caseEntityExsiting.getOriginalPdfPath();
			boolean overuledStatus = caseEntityExsiting.isOverruled();
			boolean liveStatus = caseEntityExsiting.isLive();
			Date createdDate = caseEntityExsiting.getCreatedDate();
			this.caseRepository.delete(caseEntityExsiting);
			System.out.println("Delete Success------------");
			CitationRequest citationRequest = addCaseBo.getCitationRequest();
			String citationYear = citationRequest.getYear();
			String citationPageNumber = citationRequest.getPageNumber();
			String otherCitation = citationRequest.getOtherCitation();
			CitationCategoryEntity citationCategoryEntity = this.citationCategoryRepository
					.findById(Integer.valueOf(citationRequest.getCitationCategoryId())).get();
			JournalEntity journalEntity = this.journalRepository
					.findById(Integer.valueOf(citationRequest.getJournalId())).get();
			CitationEntity citationEntity = new CitationEntity();
			citationEntity.setYear(citationYear);
			citationEntity.setPageNumber(citationPageNumber);
			citationEntity.setOtherCitation(otherCitation);
			citationEntity.setJournalEntity(journalEntity);
			citationEntity.setCitationCategoryEntity(citationCategoryEntity);
			
			citationEntity.setSecondaryCitationEntity(null);
			if(journalEntity.getJournalType().equalsIgnoreCase(AppConstant.CommonConstant.JOURNAL_TYPE_SAR_ONLINE)){
				
				CitationCategoryEntity secondaryCitationCategoryEntity = this.citationCategoryRepository
						.findById(Integer.valueOf(citationRequest.getSecondaryCitationCategoryId())).get();
				JournalEntity secondaryJournalEntity = this.journalRepository
						.findById(Integer.valueOf(citationRequest.getSecondaryJournalId())).get();
				
				
				System.out.println("--------------Secondary Journal ID--------------- "+citationRequest.getSecondaryJournalId());
				
				System.out.println("--------------Journal Name--------------- "+citationRequest.getSecondaryJournalId());
				
				String secondaryCitationYear = citationRequest.getSecondaryYear();
				String secondaryCitationPageNumber = citationRequest.getSecondaryPageNumber();
			
				SecondaryCitationEntity secondaryCitationEntity = new SecondaryCitationEntity();
				secondaryCitationEntity.setCitationCategoryEntity(secondaryCitationCategoryEntity);
				secondaryCitationEntity.setPageNumber(secondaryCitationPageNumber);
				secondaryCitationEntity.setYear(secondaryCitationYear);
				secondaryCitationEntity.setJournalEntity(secondaryJournalEntity);
				
				citationEntity.setSecondaryCitationEntity(secondaryCitationEntity);
				
			}
			
			CourtDetailRequest courtDetailRequest = addCaseBo.getCourtDetailRequest();
			String allJudges = courtDetailRequest.getAllJudges();
			CourtBenchEntity courtBenchEntity = this.courtBenchRepository
					.findById(Integer.valueOf(courtDetailRequest.getCourtBenchId())).get();
			CourtBranchEntity courtBranchEntity = this.courtBranchRepository
					.findById(Integer.valueOf(courtDetailRequest.getCourtBranchId())).get();
			CourtEntity courtEntity = this.courtRepository.findById(Integer.valueOf(courtDetailRequest.getCourtId()))
					.get();
			CourtDetailEntity courtDetailEntity = new CourtDetailEntity();
			courtDetailEntity.setAllJudges(allJudges);
			courtDetailEntity.setCourtBenchEntity(courtBenchEntity);
			courtDetailEntity.setCourtBranchEntity(courtBranchEntity);
			courtDetailEntity.setCourtEntity(courtEntity);
			List<AdditionalAppellantRespondentEntity> additionalAppellantRespondentEntityList = new ArrayList<>();
			List<AdditionalAppellantRespondentRequest> additionalAppellantRespondentRequestList = addCaseBo
					.getAdditionalAppellantRespondentRequestList();
			if (additionalAppellantRespondentRequestList != null)
				for (AdditionalAppellantRespondentRequest req : additionalAppellantRespondentRequestList) {
					AdditionalAppellantRespondentEntity additionalAppellantRespondentEntity = new AdditionalAppellantRespondentEntity();
					additionalAppellantRespondentEntity.setCaseNumber(req.getCase_number() + "");
					additionalAppellantRespondentEntity.setExtraCaseAndYear(req.getExtraCaseAndYear());
					additionalAppellantRespondentEntity.setRespondent(req.getRespondent());
					additionalAppellantRespondentEntity.setAppellant(req.getAppellant());
					additionalAppellantRespondentEntityList.add(additionalAppellantRespondentEntity);
				}
			CaseHistoryRequest caseHistoryRequest = addCaseBo.getCaseHistoryRequest();
			String caseNumber = caseHistoryRequest.getCaseNumber();
			String year = caseHistoryRequest.getYear();
			String decidedDate = caseHistoryRequest.getDecidedDay();
			String decidedMonth = caseHistoryRequest.getDecidedMonth();
			String decidedYear = caseHistoryRequest.getDecidedYear();
			String caseHistoryNote = caseHistoryRequest.getNotes();
			CaseHistoryEntity caseHistoryEntity = new CaseHistoryEntity();
			caseHistoryEntity.setDecided_day(decidedDate);
			caseHistoryEntity.setDecidedMonth(decidedMonth);
			caseHistoryEntity.setYear(year);
			caseHistoryEntity.setNotes(caseHistoryNote);
			caseHistoryEntity.setCaseNumber(caseNumber);
			caseHistoryEntity.setDecidedYear(decidedYear);
			String remarkable = addCaseBo.getRemarkable();
			List<HeadnoteEntity> headnoteEntityList = new ArrayList<>();
			List<HeadnoteRequest> headnoteRequestList = addCaseBo.getHeadNoteRequestList();
			for (HeadnoteRequest req : headnoteRequestList) {
				HeadnoteEntity headnoteEntity = new HeadnoteEntity();
				headnoteEntity.setActname1(req.getActname1());
				headnoteEntity.setActname2(req.getActname2());
				headnoteEntity.setActname3(req.getActname3());
				headnoteEntity.setActname4(req.getActname4());
				headnoteEntity.setHeadnote(req.getHeadnote());
				headnoteEntity.setPriority(req.getPriority());
				headnoteEntity.setSection1(req.getSection1());
				headnoteEntity.setSection2(req.getSection2());
				headnoteEntity.setSection3(req.getSection3());
				headnoteEntity.setSection4(req.getSection4());
				headnoteEntity.setTopic(req.getTopic());
				headnoteEntity.setParagraph(req.getParagraph());
				
				/**added for update  in headnote **/
				headnoteEntity.setOrder1(req.getOrder1());
				headnoteEntity.setOrder2(req.getOrder2());
				headnoteEntity.setOrder3(req.getOrder3());
				headnoteEntity.setOrder4(req.getOrder4());
				
				headnoteEntity.setRule1(req.getRule1());
				headnoteEntity.setRule2(req.getRule2());
				headnoteEntity.setRule3(req.getRule3());
				headnoteEntity.setRule4(req.getRule4());
				
				headnoteEntity.setArticle1(req.getArticle1());
				headnoteEntity.setArticle2(req.getArticle2());
				headnoteEntity.setArticle3(req.getArticle3());
				headnoteEntity.setArticle4(req.getArticle4());
				
				headnoteEntityList.add(headnoteEntity);
			}
			List<CasesRefferedEntity> casesRefferedEntityList = new ArrayList<>();
			List<CasesRefferedRequest> casesRefferedRequestList = addCaseBo.getCasesReferredRequestList();
			for (CasesRefferedRequest req : casesRefferedRequestList) {
				CasesRefferedEntity casesRefferedEntity = new CasesRefferedEntity();
				casesRefferedEntity.setCasesReferred(req.getCasesReferred());
				casesRefferedEntity.setPartyName(req.getPartyName());
				casesRefferedEntity.setLinkedDocId(req.getLinkedDocId());
				casesRefferedEntityList.add(casesRefferedEntity);
			}
			SingleCouncilDetailRequest singleCouncilDetailRequest = null;
			DoubleCouncilDetailRequest doubleCouncilDetailRequest = null;
			DoubleCouncilDetailEntity detailEntity = null;
			SingleCouncilDetailEntity singleCouncilDetailEntity = null;
			if (addCaseBo.getSingleCouncilDetailRequest() != null) {
				singleCouncilDetailRequest = addCaseBo.getSingleCouncilDetailRequest();
				singleCouncilDetailEntity = new SingleCouncilDetailEntity();
				singleCouncilDetailEntity.setPetionerName(singleCouncilDetailRequest.getPetitionerName());
			} else if (addCaseBo.getDoubleCouncilDetailRequest() != null) {
				doubleCouncilDetailRequest = addCaseBo.getDoubleCouncilDetailRequest();
				detailEntity = new DoubleCouncilDetailEntity();
				detailEntity.setAdvocateForAppellant(doubleCouncilDetailRequest.getAdvocateForAppellant());
				detailEntity.setAdvocateForRespondent(doubleCouncilDetailRequest.getAdvocateForRespondent());
				detailEntity.setExtraCouncilDetails(doubleCouncilDetailRequest.getExtraCouncilDetails());
			}
			String judgeName = addCaseBo.getJudgeName();
			String judgementType = addCaseBo.getJudgementType();
			String caseResult = addCaseBo.getCaseResult();
			String judgementOrder = addCaseBo.getJudgementOrder();
			String appellant = addCaseBo.getAppellant();
			String respondent = addCaseBo.getRespondent();
			CaseEntity caseEntity = new CaseEntity();
			if (additionalAppellantRespondentEntityList.size() > 0)
				caseEntity.setAdditionalAppellantRespondentEntitySet(additionalAppellantRespondentEntityList);
			caseEntity.setAppellant(appellant);
			caseEntity.setCaseHistoryEntity(caseHistoryEntity);
			caseEntity.setCaseResult(caseResult);
			caseEntity.setCasesReferredEntitySet(casesRefferedEntityList);
			caseEntity.setCitationEntity(citationEntity);
			caseEntity.setCourtDetailEntity(courtDetailEntity);
			caseEntity.setDocId(docId);
			caseEntity.setHeadNoteEntitySet(headnoteEntityList);
			caseEntity.setJudgementOrder(judgementOrder);
			caseEntity.setJudgementType(judgementType);
			caseEntity.setJudgeName(judgeName);
			caseEntity.setOriginalPdfPath(existingPath);
			caseEntity.setRemarkable(remarkable);
			caseEntity.setRespondent(respondent);
			caseEntity.setOverruled(overuledStatus);
			caseEntity.setLive(liveStatus);
			caseEntity.setCreatedDate(createdDate);
			for (HeadnoteEntity headnoteEntity : headnoteEntityList)
				headnoteEntity.setCaseEntity(caseEntity);
			for (CasesRefferedEntity casesReferredEntity : casesRefferedEntityList)
				casesReferredEntity.setCaseEntity(caseEntity);
			for (AdditionalAppellantRespondentEntity additionalAppellantRespondentEntity : additionalAppellantRespondentEntityList)
				additionalAppellantRespondentEntity.setCaseEntity(caseEntity);
			if (singleCouncilDetailEntity != null) {
				caseEntity.setSingleCouncilDetailEntity(singleCouncilDetailEntity);
			} else if (detailEntity != null) {
				caseEntity.setDoubleCouncilDetailEntity(detailEntity);
			}
			this.caseRepository.save(caseEntity);
			commonMessageResponse = new CommonMessageResponse();
			commonMessageResponse.setMsg("Case Edit was successfull. Changes saved");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> EditCaseService()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonMessageResponse;
	}

	private String saveCasePdfFile(MultipartFile file, Long docId) {
		try {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			String name = file.getOriginalFilename();
			System.out.println("------ORIGINAL FILE NAME----" + name);
			String extension = null;
			int lastIndexOf = name.lastIndexOf(".");
			if (lastIndexOf == -1) {
				extension = "";
			} else {
				extension = name.substring(lastIndexOf);
			}
			String fileName = "CASE_" + docId + extension;
			System.out.println("---------EXTENSION--------" + extension);
			File newFile = new File(this.fileUploadDirectory + "/" + fileName);
			inputStream = file.getInputStream();
			if (!newFile.exists())
				newFile.createNewFile();
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1)
				outputStream.write(bytes, 0, read);
			outputStream.close();
			String absolutePath = newFile.getAbsolutePath();
			return absolutePath;
		} catch (IOException e) {
			e.printStackTrace();
			AppException appException = new AppException("File Upload ERROR", "117", "Unable to upload case file.");
			throw appException;
		}
	}

	public CommonMessageResponse uploadCasePdf(UploadCasePdfBo uploadCasePdfBo, Long docId) {
		CommonMessageResponse commonMessageResponse = null;
		try {
			String originalPdfPath = saveCasePdfFile(uploadCasePdfBo.getFile(), docId);
			CaseEntity caseEntity = this.caseRepository.findByDocId(docId.longValue());
			if (caseEntity == null)
				throw new AppException("File Upload ERROR", "117",
						"No case registered with the DocId given to upload PDF file");
			System.out.println("-------------ORIGINAL PDF PATH-----------" + originalPdfPath);
			caseEntity.setOriginalPdfPath(originalPdfPath);
			commonMessageResponse = new CommonMessageResponse("PDF File Uploaded Successfully");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> uploadCasePdf()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonMessageResponse;
	}

	public CommonPaginationResponse getCaseList(GetCaseListBo getCaseListBo, int pageNumber, int perPage) {
		CommonPaginationResponse commonPaginationResponse = null;
	
		try {
			if (pageNumber > 0)
				pageNumber--;
			PageRequest pageRequest = PageRequest.of(pageNumber, perPage);
			String caseCategory = getCaseListBo.getCaseCategory();
			String courCategory = getCaseListBo.getCourCategory();
			String overRuled = getCaseListBo.getOverRuled();
			String live = getCaseListBo.getLive();
			String searchValue = getCaseListBo.getSearchValue();
			if (caseCategory.equals("ALL") && courCategory.equals("ALL") && overRuled.equals("ALL")
					&& live.equals("ALL") && searchValue.equals("")) {
				Page<Object> page = null;
				page = this.caseRepository.findAllByActive(true, (Pageable) pageRequest);
				List<Object> list = page.getContent();
				List<GetCaseListResponse> list1 = new ArrayList<>();
				for (int j = 0; j < list.size(); j++) {
					Object[] arr = (Object[]) list.get(j);
					long docId = ((Long) arr[0]).longValue();
					String appellant = String.valueOf(arr[1]);
					String respondent = String.valueOf(arr[2]);
					String caseCategoryResult = String.valueOf(arr[3]);
					String courtType = String.valueOf(arr[4]);
					boolean isOveruled = ((Boolean) arr[5]).booleanValue();
					boolean isLive = ((Boolean) arr[6]).booleanValue();
					String createdBy = String.valueOf(arr[7]);
					Date createdDate = (Date) arr[8];
					String originalPdfPath = String.valueOf(arr[9]);
					if (originalPdfPath.equals("null"))
						originalPdfPath = null;
					GetCaseListResponse getCaseListResponse = new GetCaseListResponse();
					getCaseListResponse.setOriginalPdfPath(originalPdfPath);
					getCaseListResponse.setAppellant(appellant);
					getCaseListResponse.setCreatedBy(createdBy);
					getCaseListResponse.setCreatedDate(createdDate);
					getCaseListResponse.setDocId(docId);
					getCaseListResponse.setLive(isLive);
					getCaseListResponse.setOverruled(isOveruled);
					getCaseListResponse.setRespondent(respondent);
					CourtDetailResponse courtDetailResponse = new CourtDetailResponse();
					CourtResponse courtresponse = new CourtResponse();
					courtresponse.setCourtType(courtType);
					courtDetailResponse.setCourtResponse(courtresponse);
					CitationCategoryResponse citationCategoryResponse = new CitationCategoryResponse();
					citationCategoryResponse.setCitationCategoryName(caseCategoryResult);
					CitationResponse citationResponse = new CitationResponse();
					citationResponse.setCitationCategoryResponse(citationCategoryResponse);
					getCaseListResponse.setCitationResponse(citationResponse);
					getCaseListResponse.setCourtDetailResponse(courtDetailResponse);
					list1.add(getCaseListResponse);
				}
				commonPaginationResponse = new CommonPaginationResponse();
				commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(page.getTotalPages());
				commonPaginationResponse.setOjectList(list1);
				return commonPaginationResponse;
			}
			List<String> caseCategoryList = new ArrayList<>();
			List<String> courtCategoryList = new ArrayList<>();
			List<Boolean> overRuledList = new ArrayList<>();
			List<Boolean> liveList = new ArrayList<>();
			Page<Object> caseEntityPage = null;
			if (caseCategory.equals("ALL")) {
				Iterable<CitationCategoryEntity> citationList = this.citationCategoryRepository.findAll();
				Iterator<CitationCategoryEntity> citationIterator = citationList.iterator();
				while (citationIterator.hasNext())
					caseCategoryList.add(((CitationCategoryEntity) citationIterator.next()).getCitationCategoryName());
			}
			if (courCategory.equals("ALL")) {
				Iterable<CourtEntity> courtList = this.courtRepository.findAll();
				Iterator<CourtEntity> citationIterator = courtList.iterator();
				while (citationIterator.hasNext())
					courtCategoryList.add(((CourtEntity) citationIterator.next()).getCourtType());
			}
			if (overRuled.equals("ALL")) {
				overRuledList.add(Boolean.valueOf(true));
				overRuledList.add(Boolean.valueOf(false));
			}
			if (live.equals("ALL")) {
				liveList.add(Boolean.valueOf(true));
				liveList.add(Boolean.valueOf(false));
			}
			if (live.equals("YES"))
				liveList.add(Boolean.valueOf(true));
			if (live.equals("NO"))
				liveList.add(Boolean.valueOf(false));
			if (overRuled.equals("YES"))
				overRuledList.add(Boolean.valueOf(true));
			if (overRuled.equals("NO"))
				overRuledList.add(Boolean.valueOf(false));
			if (caseCategoryList.size() == 0)
				caseCategoryList.add(caseCategory);
			if (courtCategoryList.size() == 0)
				courtCategoryList.add(courCategory);
			if (searchValue.equals("")) {
				caseEntityPage = this.caseDao.getCasePage((Pageable) pageRequest, courtCategoryList, caseCategoryList,
						liveList, overRuledList);
			} else if (!searchValue.equals("")) {
				try {
					Long searchValueInt = Long.valueOf(Long.parseLong(searchValue));
					System.out.println("--------------------SEARCH VALUE------------" + searchValueInt);
					caseEntityPage = this.caseDao.getCasePageInt((Pageable) pageRequest, courtCategoryList,
							caseCategoryList, liveList, overRuledList, searchValueInt);
				} catch (NumberFormatException e) {
					System.out.println("HERE");
					caseEntityPage = this.caseDao.getCasePage((Pageable) pageRequest, courtCategoryList,
							caseCategoryList, liveList, overRuledList, "%" + searchValue + "%");
				}
			}
			System.out.println("--------------------QUERY FINISHED-----------------------");
			List<Object> caseEntityList = caseEntityPage.getContent();
			List<GetCaseListResponse> getCaseResponseList = new ArrayList<>();
		
			for (int i = 0; i < caseEntityList.size(); i++) {
				Object[] arr = (Object[]) caseEntityList.get(i);
				long docId = ((Long) arr[0]).longValue();
				String appellant = String.valueOf(arr[1]);
				String respondent = String.valueOf(arr[2]);
				String caseCategoryResult = String.valueOf(arr[3]);
				String courtType = String.valueOf(arr[4]);
				boolean isOveruled = ((Boolean) arr[5]).booleanValue();
				boolean isLive = ((Boolean) arr[6]).booleanValue();
				String createdBy = String.valueOf(arr[7]);
				Date createdDate = (Date) arr[8];
				String originalPdfPath = String.valueOf(arr[9]);
				if (originalPdfPath.equals("null"))
					originalPdfPath = null;
				GetCaseListResponse getCaseListResponse = new GetCaseListResponse();
				getCaseListResponse.setOriginalPdfPath(originalPdfPath);
				getCaseListResponse.setAppellant(appellant);
				getCaseListResponse.setCreatedBy(createdBy);
				getCaseListResponse.setCreatedDate(createdDate);
				getCaseListResponse.setDocId(docId);
				getCaseListResponse.setLive(isLive);
				getCaseListResponse.setOverruled(isOveruled);
				getCaseListResponse.setRespondent(respondent);
				CourtDetailResponse courtDetailResponse = new CourtDetailResponse();
				CourtResponse courtresponse = new CourtResponse();
				courtresponse.setCourtType(courtType);
				courtDetailResponse.setCourtResponse(courtresponse);
				CitationCategoryResponse citationCategoryResponse = new CitationCategoryResponse();
				citationCategoryResponse.setCitationCategoryName(caseCategoryResult);
				CitationResponse citationResponse = new CitationResponse();
				citationResponse.setCitationCategoryResponse(citationCategoryResponse);
				getCaseListResponse.setCitationResponse(citationResponse);
				getCaseListResponse.setCourtDetailResponse(courtDetailResponse);
				getCaseResponseList.add(getCaseListResponse);
			}
			commonPaginationResponse = new CommonPaginationResponse();
			commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(caseEntityPage.getTotalPages());
			commonPaginationResponse.setOjectList(getCaseResponseList);
			return commonPaginationResponse;
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			String errorMessage = "Error in CaseServiceImpl --> getCaseList()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
	}
	
	
	/********************Added for the functionality where Data entry operator can only see his own added cases*********************************/
	
	public CommonPaginationResponse getCaseListForDataEntryOperator(GetCaseListBo getCaseListBo, int pageNumber, int perPage,String email) {
		CommonPaginationResponse commonPaginationResponse = null;
	
		try {
			if (pageNumber > 0)
				pageNumber--;
			PageRequest pageRequest = PageRequest.of(pageNumber, perPage);
			String caseCategory = getCaseListBo.getCaseCategory();
			String courCategory = getCaseListBo.getCourCategory();
			String overRuled = getCaseListBo.getOverRuled();
			String live = getCaseListBo.getLive();
			String searchValue = getCaseListBo.getSearchValue();
			if (caseCategory.equals("ALL") && courCategory.equals("ALL") && overRuled.equals("ALL")
					&& live.equals("ALL") && searchValue.equals("")) {
				Page<Object> page = null;
				page = this.caseRepository.findAllByActiveForDataEntryOperator(true,email, (Pageable) pageRequest);
				List<Object> list = page.getContent();
				List<GetCaseListResponse> list1 = new ArrayList<>();
				for (int j = 0; j < list.size(); j++) {
					Object[] arr = (Object[]) list.get(j);
					long docId = ((Long) arr[0]).longValue();
					String appellant = String.valueOf(arr[1]);
					String respondent = String.valueOf(arr[2]);
					String caseCategoryResult = String.valueOf(arr[3]);
					String courtType = String.valueOf(arr[4]);
					boolean isOveruled = ((Boolean) arr[5]).booleanValue();
					boolean isLive = ((Boolean) arr[6]).booleanValue();
					String createdBy = String.valueOf(arr[7]);
					Date createdDate = (Date) arr[8];
					String originalPdfPath = String.valueOf(arr[9]);
					if (originalPdfPath.equals("null"))
						originalPdfPath = null;
					GetCaseListResponse getCaseListResponse = new GetCaseListResponse();
					getCaseListResponse.setOriginalPdfPath(originalPdfPath);
					getCaseListResponse.setAppellant(appellant);
					getCaseListResponse.setCreatedBy(createdBy);
					getCaseListResponse.setCreatedDate(createdDate);
					getCaseListResponse.setDocId(docId);
					getCaseListResponse.setLive(isLive);
					getCaseListResponse.setOverruled(isOveruled);
					getCaseListResponse.setRespondent(respondent);
					CourtDetailResponse courtDetailResponse = new CourtDetailResponse();
					CourtResponse courtresponse = new CourtResponse();
					courtresponse.setCourtType(courtType);
					courtDetailResponse.setCourtResponse(courtresponse);
					CitationCategoryResponse citationCategoryResponse = new CitationCategoryResponse();
					citationCategoryResponse.setCitationCategoryName(caseCategoryResult);
					CitationResponse citationResponse = new CitationResponse();
					citationResponse.setCitationCategoryResponse(citationCategoryResponse);
					getCaseListResponse.setCitationResponse(citationResponse);
					getCaseListResponse.setCourtDetailResponse(courtDetailResponse);
					list1.add(getCaseListResponse);
				}
				commonPaginationResponse = new CommonPaginationResponse();
				commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(page.getTotalPages());
				commonPaginationResponse.setOjectList(list1);
				return commonPaginationResponse;
			}
			List<String> caseCategoryList = new ArrayList<>();
			List<String> courtCategoryList = new ArrayList<>();
			List<Boolean> overRuledList = new ArrayList<>();
			List<Boolean> liveList = new ArrayList<>();
			Page<Object> caseEntityPage = null;
			if (caseCategory.equals("ALL")) {
				Iterable<CitationCategoryEntity> citationList = this.citationCategoryRepository.findAll();
				Iterator<CitationCategoryEntity> citationIterator = citationList.iterator();
				while (citationIterator.hasNext())
					caseCategoryList.add(((CitationCategoryEntity) citationIterator.next()).getCitationCategoryName());
			}
			if (courCategory.equals("ALL")) {
				Iterable<CourtEntity> courtList = this.courtRepository.findAll();
				Iterator<CourtEntity> citationIterator = courtList.iterator();
				while (citationIterator.hasNext())
					courtCategoryList.add(((CourtEntity) citationIterator.next()).getCourtType());
			}
			if (overRuled.equals("ALL")) {
				overRuledList.add(Boolean.valueOf(true));
				overRuledList.add(Boolean.valueOf(false));
			}
			if (live.equals("ALL")) {
				liveList.add(Boolean.valueOf(true));
				liveList.add(Boolean.valueOf(false));
			}
			if (live.equals("YES"))
				liveList.add(Boolean.valueOf(true));
			if (live.equals("NO"))
				liveList.add(Boolean.valueOf(false));
			if (overRuled.equals("YES"))
				overRuledList.add(Boolean.valueOf(true));
			if (overRuled.equals("NO"))
				overRuledList.add(Boolean.valueOf(false));
			if (caseCategoryList.size() == 0)
				caseCategoryList.add(caseCategory);
			if (courtCategoryList.size() == 0)
				courtCategoryList.add(courCategory);
			if (searchValue.equals("")) {
				caseEntityPage = this.caseDao.getCasePageForDataEntry((Pageable) pageRequest, courtCategoryList, caseCategoryList,
						liveList, overRuledList,email);
			} else if (!searchValue.equals("")) {
				try {
					Long searchValueInt = Long.valueOf(Long.parseLong(searchValue));
					System.out.println("--------------------SEARCH VALUE------------" + searchValueInt);
					caseEntityPage = this.caseDao.getCasePageIntForDataEntry((Pageable) pageRequest, courtCategoryList,
							caseCategoryList, liveList, overRuledList, searchValueInt,email);
				} catch (NumberFormatException e) {
					System.out.println("HERE");
					caseEntityPage = this.caseDao.getCasePageForDataEntry((Pageable) pageRequest, courtCategoryList,
							caseCategoryList, liveList, overRuledList, "%" + searchValue + "%", email);
				}
			}
			System.out.println("--------------------QUERY FINISHED-----------------------");
			List<Object> caseEntityList = caseEntityPage.getContent();
			List<GetCaseListResponse> getCaseResponseList = new ArrayList<>();
		
			for (int i = 0; i < caseEntityList.size(); i++) {
				Object[] arr = (Object[]) caseEntityList.get(i);
				long docId = ((Long) arr[0]).longValue();
				String appellant = String.valueOf(arr[1]);
				String respondent = String.valueOf(arr[2]);
				String caseCategoryResult = String.valueOf(arr[3]);
				String courtType = String.valueOf(arr[4]);
				boolean isOveruled = ((Boolean) arr[5]).booleanValue();
				boolean isLive = ((Boolean) arr[6]).booleanValue();
				String createdBy = String.valueOf(arr[7]);
				Date createdDate = (Date) arr[8];
				String originalPdfPath = String.valueOf(arr[9]);
				GetCaseListResponse getCaseListResponse = new GetCaseListResponse();
				getCaseListResponse.setOriginalPdfPath(originalPdfPath);
				getCaseListResponse.setAppellant(appellant);
				getCaseListResponse.setCreatedBy(createdBy);
				getCaseListResponse.setCreatedDate(createdDate);
				getCaseListResponse.setDocId(docId);
				getCaseListResponse.setLive(isLive);
				getCaseListResponse.setOverruled(isOveruled);
				getCaseListResponse.setRespondent(respondent);
				CourtDetailResponse courtDetailResponse = new CourtDetailResponse();
				CourtResponse courtresponse = new CourtResponse();
				courtresponse.setCourtType(courtType);
				courtDetailResponse.setCourtResponse(courtresponse);
				CitationCategoryResponse citationCategoryResponse = new CitationCategoryResponse();
				citationCategoryResponse.setCitationCategoryName(caseCategoryResult);
				CitationResponse citationResponse = new CitationResponse();
				citationResponse.setCitationCategoryResponse(citationCategoryResponse);
				getCaseListResponse.setCitationResponse(citationResponse);
				getCaseListResponse.setCourtDetailResponse(courtDetailResponse);
				getCaseResponseList.add(getCaseListResponse);
			}
			commonPaginationResponse = new CommonPaginationResponse();
			commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(caseEntityPage.getTotalPages());
			commonPaginationResponse.setOjectList(getCaseResponseList);
			return commonPaginationResponse;
		} catch (AppException appException) {
			appException.printStackTrace();
			throw appException;
		} catch (Exception ex) {
			String errorMessage = "Error in CaseServiceImpl --> getCaseList()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			ex.printStackTrace();
			throw appException;
		}
	}


	public Resource getCasePdf(long docId) {
		try {
			String pdf = this.caseDao.getPdfPathByDocId(docId);
			if (pdf == null)
				throw new AppException("118", "Get PDF ERROR",
						"No pdf file is present for the given case.Please try with difference docId");
			String[] fileArryaSplit = pdf.split("/");
			String fileName = fileArryaSplit[fileArryaSplit.length - 1];
			System.out.println("-----------EXACT FILE NAME----------" + fileName);
			Path fileStorageLocation = Paths.get(this.fileUploadDirectory, new String[0]).toAbsolutePath().normalize();
			Path filePath = fileStorageLocation.resolve(fileName);
			UrlResource urlResource = new UrlResource(filePath.toUri());
			if (urlResource.exists())
				return (Resource) urlResource;
			throw new RuntimeException("File not found ");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> getCasePdf()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
	}

	public CommonMessageResponse deleteCaseService(int docId) {
		CommonMessageResponse commonMessageResponse = null;
		try {
			CaseEntity caseEntity = this.caseRepository.getCaseEntityByPrimaryKeyAndDocId(docId);
			if (caseEntity == null)
				throw new AppException("Delete case ERROR", "120",
						"No Case available for the given docId. So delete case operation was unsuccessfull");
			caseEntity.setActive(Boolean.valueOf(false));
			commonMessageResponse = new CommonMessageResponse();
			commonMessageResponse.setMsg("Case Delete was successfull. Case Deleted");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> deleteCaseService()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonMessageResponse;
	}

	public GetCaseListResponseForSingleCase getSingleCase(int docId) {
		
		CaseEntity caseEntity = this.caseDao.getSingleCaseByDocId(docId);
		if (caseEntity == null)
			throw new AppException("Get Single case ERROR", "121",
					"No Case available for the given docId. So get single case operation was unsuccessfull");
		GetCaseListResponseForSingleCase getCaseListResponse = new GetCaseListResponseForSingleCase();
		BeanUtils.copyProperties(caseEntity, getCaseListResponse);
		CourtDetailResponseForSingleCase courtDetailResponse = new CourtDetailResponseForSingleCase();
		CourtResponseForSingleCase courtResponse = new CourtResponseForSingleCase();
		CourtDetailEntity courtDetailEntity = caseEntity.getCourtDetailEntity();
		CourtBranchEntity courBranchEntity = courtDetailEntity.getCourtBranchEntity();
		CourtBenchEntity courtBenchEntity = courtDetailEntity.getCourtBenchEntity();
		CourtEntity courtEntity = courtDetailEntity.getCourtEntity();
		courtResponse.setLabel(courtEntity.getCourtType());
		courtResponse.setValue(courtEntity.getCourtType());
		courtResponse.setId(courtEntity.getId().intValue());
		CourtBranchResponseForSingleCase courtBranchResponse = new CourtBranchResponseForSingleCase();
		courtBranchResponse.setLabel(courBranchEntity.getBranchName());
		courtBranchResponse.setValue(courBranchEntity.getBranchName());
		courtBranchResponse.setId(courBranchEntity.getId().intValue());
		CourtBenchResponseForSingleCase courtBenchResponse = new CourtBenchResponseForSingleCase();
		courtBenchResponse.setLabel(courtBenchEntity.getBenchName());
		courtBenchResponse.setValue(courtBenchEntity.getBenchName());
		courtBenchResponse.setId(courtBenchEntity.getId().intValue());
		courtDetailResponse.setAllJudges(courtDetailEntity.getAllJudges());
		courtDetailResponse.setCourtBenchResponse(courtBenchResponse);
		courtDetailResponse.setCourtBranchResponse(courtBranchResponse);
		courtDetailResponse.setCourtResponse(courtResponse);
		getCaseListResponse.setCourtDetailResponse(courtDetailResponse);
		
		CitationEntity citationEntity = caseEntity.getCitationEntity();
		CitationCategoryEntity citationCategoryEntity = citationEntity.getCitationCategoryEntity();
		JournalEntity journalEntity = citationEntity.getJournalEntity();
		CitationCategoryResponseForSingleCase citationCategoryResponse = new CitationCategoryResponseForSingleCase();
		citationCategoryResponse.setLabel(citationCategoryEntity.getCitationCategoryName());
		citationCategoryResponse.setValue(citationCategoryEntity.getCitationCategoryName());
		citationCategoryResponse.setId(citationCategoryEntity.getId().intValue());
		JournalResponseForSingleCase journalResponse = new JournalResponseForSingleCase();
		journalResponse.setLabel(journalEntity.getJournalType());
		journalResponse.setValue(journalEntity.getJournalType());
		journalResponse.setId(journalEntity.getId().intValue());
		CitationResponseForSingleCase citationResponse = new CitationResponseForSingleCase();
		citationResponse.setCitationCategoryResponse(citationCategoryResponse);
		citationResponse.setJournalResponse(journalResponse);
		citationResponse.setOtherCitation(citationEntity.getOtherCitation());
		citationResponse.setPageNumber(citationEntity.getPageNumber());
		YearResponse yearResponse = new YearResponse(citationEntity.getYear(), citationEntity.getYear(),
				Integer.parseInt(citationEntity.getYear()));
		citationResponse.setYear(yearResponse);
		
		citationResponse.setSecondaryCitationResponseForSingleCase(null);
		SecondaryCitationEntity secondaryCitationEntity = citationEntity.getSecondaryCitationEntity();
		
		if(secondaryCitationEntity!=null){
			
			SecondaryCitationResponseForSingleCase secondaryCitationResponseForSingleCase = new SecondaryCitationResponseForSingleCase();
			
			CitationCategoryEntity secondaryCitationCategoryEntity = secondaryCitationEntity.getCitationCategoryEntity();
			JournalEntity secondaryJournalEntity = secondaryCitationEntity.getJournalEntity();
			CitationCategoryResponseForSingleCase secondaryCitationCategoryResponseForSingleCase = new CitationCategoryResponseForSingleCase();
			secondaryCitationCategoryResponseForSingleCase.setLabel(secondaryCitationCategoryEntity.getCitationCategoryName());
			secondaryCitationCategoryResponseForSingleCase.setValue(secondaryCitationCategoryEntity.getCitationCategoryName());
			secondaryCitationCategoryResponseForSingleCase.setId(secondaryCitationCategoryEntity.getId().intValue());
			JournalResponseForSingleCase secondaryJournalResponse = new JournalResponseForSingleCase();
			secondaryJournalResponse.setLabel(secondaryJournalEntity.getJournalType());
			secondaryJournalResponse.setValue(secondaryJournalEntity.getJournalType());
			secondaryJournalResponse.setId(secondaryJournalEntity.getId().intValue());
			secondaryCitationResponseForSingleCase.setCitationCategoryResponse(secondaryCitationCategoryResponseForSingleCase);
			secondaryCitationResponseForSingleCase.setJournalResponse(secondaryJournalResponse);

			secondaryCitationResponseForSingleCase.setPageNumber(secondaryCitationEntity.getPageNumber());
			YearResponse secondaryYearResponse = new YearResponse(secondaryCitationEntity.getYear(), secondaryCitationEntity.getYear(),
					Integer.parseInt(secondaryCitationEntity.getYear()));
			secondaryCitationResponseForSingleCase.setYear(secondaryYearResponse);
			citationResponse.setSecondaryCitationResponseForSingleCase(secondaryCitationResponseForSingleCase);
			
		 
		}
		
		
		
		
		
		getCaseListResponse.setCitationResponse(citationResponse);
		
		
		List<AdditionalAppellantRespondentResponse> additionalAppellantRespondentResponses = new ArrayList<>();
		for (AdditionalAppellantRespondentEntity e : caseEntity.getAdditionalAppellantRespondentEntitySet()) {
			AdditionalAppellantRespondentResponse additionalAppellantRespondentResponse = new AdditionalAppellantRespondentResponse();
			BeanUtils.copyProperties(e, additionalAppellantRespondentResponse);
			additionalAppellantRespondentResponses.add(additionalAppellantRespondentResponse);
		}
		getCaseListResponse.setAdditionalAppellantRespondentResponseList(additionalAppellantRespondentResponses);
		CaseHistoryResponse caseHistoryResponse = new CaseHistoryResponse();
		BeanUtils.copyProperties(caseEntity.getCaseHistoryEntity(), caseHistoryResponse);
		YearResponse yearResponseForCaseHistory = new YearResponse(caseEntity.getCaseHistoryEntity().getYear(),
				caseEntity.getCaseHistoryEntity().getYear(),
				Integer.parseInt(caseEntity.getCaseHistoryEntity().getYear()));
		YearResponse yearDecidedDay = new YearResponse(caseEntity.getCaseHistoryEntity().getDecided_day(),
				caseEntity.getCaseHistoryEntity().getDecided_day(),
				Integer.parseInt(caseEntity.getCaseHistoryEntity().getDecided_day()));
		YearResponse yearDecidedMonth = new YearResponse(caseEntity.getCaseHistoryEntity().getDecidedMonth(),
				caseEntity.getCaseHistoryEntity().getDecidedMonth(),
				Integer.parseInt(caseEntity.getCaseHistoryEntity().getDecidedMonth()));
		YearResponse yearDecidedYear = new YearResponse(caseEntity.getCaseHistoryEntity().getDecidedYear(),
				caseEntity.getCaseHistoryEntity().getDecidedYear(),
				Integer.parseInt(caseEntity.getCaseHistoryEntity().getDecidedYear()));
		caseHistoryResponse.setYear(yearResponseForCaseHistory);
		caseHistoryResponse.setDecided_day(yearDecidedDay);
		caseHistoryResponse.setDecidedMonth(yearDecidedMonth);
		caseHistoryResponse.setDecidedYear(yearDecidedYear);
		getCaseListResponse.setCaseHistoryResponse(caseHistoryResponse);
		List<CasesReferredResponse> casesReferredResponses = new ArrayList<>();
		for (CasesRefferedEntity e : caseEntity.getCasesReferredEntitySet()) {
			CasesReferredResponse casesReferredResponse = new CasesReferredResponse();
			BeanUtils.copyProperties(e, casesReferredResponse);
			casesReferredResponses.add(casesReferredResponse);
		}
		getCaseListResponse.setCasesReferredResponseList(casesReferredResponses);
		List<HeadnoteResponse> headnoteResponses = new ArrayList<>();
		for (HeadnoteEntity e : caseEntity.getHeadNoteEntitySet()) {
			HeadnoteResponse headnoteResponse = new HeadnoteResponse();
			BeanUtils.copyProperties(e, headnoteResponse);
			headnoteResponses.add(headnoteResponse);
		}
		SingleCouncilDetailEntity singleCouncilEntity = caseEntity.getSingleCouncilDetailEntity();
		DoubleCouncilDetailEntity doubleCouncilDetailEntity = caseEntity.getDoubleCouncilDetailEntity();
		if (singleCouncilEntity != null) {
			SingleCouncileDetailResponse singleCouncilDetailResponse = new SingleCouncileDetailResponse();
			singleCouncilDetailResponse.setPetitionerName(singleCouncilEntity.getPetionerName());
			getCaseListResponse.setSingleCouncilDetailResponse(singleCouncilDetailResponse);
		} else if (doubleCouncilDetailEntity != null) {
			DoubleCouncilDetailResponse detailResponse = new DoubleCouncilDetailResponse();
			detailResponse.setAdvocateForAppellant(doubleCouncilDetailEntity.getAdvocateForAppellant());
			detailResponse.setAdvocateForRespondent(doubleCouncilDetailEntity.getAdvocateForRespondent());
			detailResponse.setExtraCouncilDetails(doubleCouncilDetailEntity.getExtraCouncilDetails());
			getCaseListResponse.setDoubleCouncilDetailResponse(detailResponse);
		}
		getCaseListResponse.setHeadnoteResponseList(headnoteResponses);
		return getCaseListResponse;
	}

	@Override
	public String getNextDocId() {
		
		String docId = String.valueOf(caseRepository.getLatestDocId());
		return docId;
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public CommonMessageResponse deletePdf(String docId) {
		CommonMessageResponse commonMessageResponse = null;
		
		try 
		{
		CaseEntity caseEntity = caseRepository.findByDocId(Long.parseLong(docId));
		caseEntity.setOriginalPdfPath(null);
		commonMessageResponse = new CommonMessageResponse();
		commonMessageResponse.setMsg("Pdf Deleted Succeddfully");
	    }
	catch (AppException appException) {
		throw appException;
	} catch (Exception ex) {
		ex.printStackTrace();
		String errorMessage = "Error in CaseServiceImpl --> deletePdf()";
		AppException appException = new AppException(
				"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
				"Error Description  :  : " + errorMessage);
		throw appException;
	}
	return commonMessageResponse;
}
	/*
	 * CommonMessageResponse commonMessageResponse = null;
		try {
			CaseEntity caseEntity = this.caseRepository.getCaseEntityByPrimaryKeyAndDocId(docId);
			if (caseEntity == null)
				throw new AppException("Delete case ERROR", "120",
						"No Case available for the given docId. So delete case operation was unsuccessfull");
			caseEntity.setActive(Boolean.valueOf(false));
			commonMessageResponse = new CommonMessageResponse();
			commonMessageResponse.setMsg("Case Delete was successfull. Case Deleted");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> deleteCaseService()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonMessageResponse;
	 */
}
