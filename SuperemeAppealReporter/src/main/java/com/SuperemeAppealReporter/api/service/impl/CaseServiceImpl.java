package com.SuperemeAppealReporter.api.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.bo.UploadCasePdfBo;
import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SucessMessage;
import com.SuperemeAppealReporter.api.exception.type.AppException;
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
import com.SuperemeAppealReporter.api.io.entity.DocIdGenerator;
import com.SuperemeAppealReporter.api.io.entity.HeadnoteEntity;
import com.SuperemeAppealReporter.api.io.entity.JournalEntity;
import com.SuperemeAppealReporter.api.io.repository.CaseRepository;
import com.SuperemeAppealReporter.api.io.repository.CitationCategoryRepository;
import com.SuperemeAppealReporter.api.io.repository.CourtBenchRepository;
import com.SuperemeAppealReporter.api.io.repository.CourtBranchRepository;
import com.SuperemeAppealReporter.api.io.repository.CourtRepository;
import com.SuperemeAppealReporter.api.io.repository.JournalReposiotry;
import com.SuperemeAppealReporter.api.service.CaseService;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.ui.model.request.AdditionalAppellantRespondentRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CaseHistoryRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CasesRefferedRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CitationRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CourtDetailRequest;
import com.SuperemeAppealReporter.api.ui.model.request.HeadnoteRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;

@Service
public class CaseServiceImpl implements CaseService{

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
	
	
	@Override
	public CommonMessageResponse addCaseService(AddCaseBo addCaseBo) {

		CommonMessageResponse commonMessageResponse = null;
		
		try
		{
		/**fetching doc id**/
		int docId = (int)addCaseBo.getDocId();
		
		/**to get the next doc Id as incremented one**/
		DocIdGenerator docIdGenerator = new DocIdGenerator();
		masterService.save(docIdGenerator);
		
		
		/**Creating CitationEntity**/
		CitationRequest citationRequest = addCaseBo.getCitationRequest();
		int citationYear = citationRequest.getYear();
		int citationPageNumber = citationRequest.getPageNumber();
		String otherCitation = citationRequest.getOtherCitation();
		CitationCategoryEntity citationCategoryEntity = citationCategoryRepository
				.findById(citationRequest.getCitationCategoryId()).get();
        JournalEntity journalEntity = journalRepository.findById(citationRequest.getJournalId()).get();
		CitationEntity citationEntity = new CitationEntity();
		citationEntity.setYear(citationYear);
		citationEntity.setPageNumber(citationPageNumber);
		citationEntity.setOtherCitation(otherCitation);
		citationEntity.setJournalEntity(journalEntity);
		citationEntity.setCitationCategoryEntity(citationCategoryEntity);
		
		/**Creating Court Detail Entity*/
		CourtDetailRequest courtDetailRequest = addCaseBo.getCourtDetailRequest();
		String allJudges = courtDetailRequest.getAllJudges();
		CourtBenchEntity courtBenchEntity = courtBenchRepository.findById(courtDetailRequest.getCourtBenchId()).get();
		CourtBranchEntity courtBranchEntity = courtBranchRepository.findById(courtDetailRequest.getCourtBranchId()).get();
		CourtEntity courtEntity = courtRepository.findById(courtDetailRequest.getCourtId()).get();
		CourtDetailEntity courtDetailEntity = new CourtDetailEntity();
		courtDetailEntity.setAllJudges(allJudges);
		courtDetailEntity.setCourtBenchEntity(courtBenchEntity);
		courtDetailEntity.setCourtBranchEntity(courtBranchEntity);
		courtDetailEntity.setCourtEntity(courtEntity);
		
		/**Creating Additional Appellant and Respondent Entity List*/
		List<AdditionalAppellantRespondentEntity> additionalAppellantRespondentEntityList
		                      = new ArrayList<AdditionalAppellantRespondentEntity>();
		List<AdditionalAppellantRespondentRequest> additionalAppellantRespondentRequestList
		                  = addCaseBo.getAdditionalAppellantRespondentRequestList();
		if(additionalAppellantRespondentRequestList!=null)
		{
		for(AdditionalAppellantRespondentRequest req : additionalAppellantRespondentRequestList)
		{
			AdditionalAppellantRespondentEntity additionalAppellantRespondentEntity =
					 new AdditionalAppellantRespondentEntity();
			additionalAppellantRespondentEntity.setCaseNumber(req.getCase_number()+"");
			additionalAppellantRespondentEntity.setExtraCaseAndYear(req.getExtraCaseAndYear());
			additionalAppellantRespondentEntity.setRespondent(req.getRespondent());
			additionalAppellantRespondentEntity.setAppellant(req.getAppellant());
			additionalAppellantRespondentEntityList.add(additionalAppellantRespondentEntity);
		}
		}
		
		/**Creating Case History Entity**/
		CaseHistoryRequest caseHistoryRequest = addCaseBo.getCaseHistoryRequest();
		int caseNumber = caseHistoryRequest.getCaseNumber();
		int year = caseHistoryRequest.getYear();
		int decidedDate = caseHistoryRequest.getDecidedDay();
		int decidedMonth  = caseHistoryRequest.getDecidedMonth();
		int decidedYear = caseHistoryRequest.getDecidedYear();
		String caseHistoryNote = caseHistoryRequest.getNotes();
		CaseHistoryEntity caseHistoryEntity = new CaseHistoryEntity();
		caseHistoryEntity.setDecided_day(decidedDate);
		caseHistoryEntity.setDecidedMonth(decidedMonth);
		caseHistoryEntity.setYear(year);
		caseHistoryEntity.setNotes(caseHistoryNote);
		caseHistoryEntity.setCaseNumber(caseNumber);
		caseHistoryEntity.setDecidedYear(decidedYear);
		
		/**Fetching Remarkable**/
		String remarkable = addCaseBo.getRemarkable();
		
		/**Creating Head note Entity List**/
		List<HeadnoteEntity> headnoteEntityList = new ArrayList<HeadnoteEntity>();
		List<HeadnoteRequest> headnoteRequestList = addCaseBo.getHeadNoteRequestList();
		for(HeadnoteRequest req : headnoteRequestList)
		{
			HeadnoteEntity headnoteEntity = new HeadnoteEntity();
			headnoteEntity.setActname1(req.getActname1());
			headnoteEntity.setActname2(req.getActname2());
			headnoteEntity.setActname3(req.getActname3());
			headnoteEntity.setHeadnote(req.getHeadnote());
			headnoteEntity.setPriority(req.getPriority());
			headnoteEntity.setSection1(req.getSection1());
			headnoteEntity.setSection2(req.getSection2());
			headnoteEntity.setSection3(req.getActname3());
			headnoteEntity.setTopic(req.getTopic());
			headnoteEntity.setParagraph(req.getParagraph());
			headnoteEntityList.add(headnoteEntity);
		}
		
		
		/**Creating cases reffered entity list**/
		List<CasesRefferedEntity> casesRefferedEntityList = new ArrayList<CasesRefferedEntity>();
		List<CasesRefferedRequest> casesRefferedRequestList = addCaseBo.getCasesReferredRequestList();
		for(CasesRefferedRequest req : casesRefferedRequestList)
		{
			CasesRefferedEntity casesRefferedEntity = new CasesRefferedEntity();
			casesRefferedEntity.setCasesReferred(req.getCasesReferred());
			casesRefferedEntity.setPartyName(req.getPartyName());
			casesRefferedEntityList.add(casesRefferedEntity);
		}
		
		/**fetching judge name**/
		String judgeName = addCaseBo.getJudgeName();
		
		/**fetching judgement Type **/
		String judgementType = addCaseBo.getJudgementType();
		
		/**fetching case result**/
		String caseResult = addCaseBo.getCaseResult();
		
		/**fetching judgment order**/
		String judgementOrder = addCaseBo.getJudgementOrder();
		
		/**fetching appellant**/
		String appellant = addCaseBo.getAppellant();
		
		/**fetching respondent**/
		String respondent = addCaseBo.getRespondent();
		
		/**Saving the case Pdf file**/
/*		
		String originalPdfPath = saveCasePdfFile(addCaseBo.getFile(),docId);*/
        
		
		/**creating case entity**/
		CaseEntity caseEntity = new CaseEntity();
		
		if(additionalAppellantRespondentEntityList.size()>0)
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
		
		
		for(HeadnoteEntity headnoteEntity : headnoteEntityList)
		{
			headnoteEntity.setCaseEntity(caseEntity);
			
		}
		
		for(CasesRefferedEntity casesReferredEntity : casesRefferedEntityList)
		{
			casesReferredEntity.setCaseEntity(caseEntity);
		}
		
  for (AdditionalAppellantRespondentEntity additionalAppellantRespondentEntity : additionalAppellantRespondentEntityList) {

	  additionalAppellantRespondentEntity.setCaseEntity(caseEntity);
	  
			}
		
        /**saving case entity**/
		caseRepository.save(caseEntity);
		
		/**generating response**/
		commonMessageResponse = new CommonMessageResponse();
		commonMessageResponse.setMsg(SucessMessage.CaseMessage.CASE_CREATED_SUCESS);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		
		{
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> addCaseService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		return commonMessageResponse;
	}
	
	/*
	 * This method saves the case PDF file and returns the saved path
	 */
	private String saveCasePdfFile(MultipartFile file,int docId)
	{
		try
		{
		    InputStream inputStream = null;
	        OutputStream outputStream = null;
	        String fileName = "CASE_"+docId;
	        File newFile = new File("src/main/resources/assets/casepdfs/" + fileName);
	        inputStream = file.getInputStream();

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
       }
            outputStream.close();
        	 return newFile.getAbsolutePath();

      
		}
            catch (IOException e) {
            	
            	e.printStackTrace();
				AppException appException
				 = new AppException(ErrorConstant.FileUploadError.ERROR_TYPE,
						 ErrorConstant.FileUploadError.ERROR_CODE,
						 ErrorConstant.FileUploadError.ERROR_MESSAGE);
				throw appException;
			}
	
	
	}

	@Override
	public CommonMessageResponse uploadCasePdf(UploadCasePdfBo uploadCasePdfBo, int docId) {
		
		CommonMessageResponse commonMessageResponse = null;
		
		try
		{
		
		String originalPdfPath = saveCasePdfFile(uploadCasePdfBo.getFile(),docId);
		
		/**updating the case pdf path in the case table**/
		CaseEntity caseEntity = caseRepository.findByDocId(docId);
		
		if(caseEntity==null)
		{
			throw new AppException(ErrorConstant.FileUploadError.ERROR_TYPE,ErrorConstant.FileUploadError.ERROR_CODE,
					ErrorConstant.FileUploadError.ERROR_MESSAGE_INVALID_DOCID);
		}
		
		caseEntity.setOriginalPdfPath(originalPdfPath);
		
		/**creating and returning common message response**/
		commonMessageResponse = new CommonMessageResponse(SucessMessage.FileUploadSuccess.FILE_UPLOAD_SUCCESS);
	
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		
		{
			ex.printStackTrace();
			String errorMessage = "Error in CaseServiceImpl --> uploadCasePdf()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
		return commonMessageResponse;
	}
	
	

	
}

