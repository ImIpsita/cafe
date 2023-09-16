package com.cafe.example.cafeExample.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cafe.example.cafeExample.constant.CafeConstant;
import com.cafe.example.cafeExample.dao.BillDao;
import com.cafe.example.cafeExample.jwt.JwtFilter;
import com.cafe.example.cafeExample.pojo.Bill;
import com.cafe.example.cafeExample.service.BillService;
import com.cafe.example.cafeExample.utils.CafeUtils;
import com.google.gson.JsonObject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	BillDao billdao;

	private Map<String, Object> mapFromJson;
	
	@Override
	public ResponseEntity<?> generatedReport(Map<String, Object> billmapReq) {
		try {
			String fileName;
			if (validateBill(billmapReq)) {
				//if file is present then it will regenerated the pdf again with the mapping uuid(not saved in database) ,otherwise created a new one
				if(billmapReq.containsKey("isGenerated") && (!(Boolean)billmapReq.get("isGenerated"))){
					fileName=billmapReq.get("uuid").toString();
				}else {
					fileName=CafeUtils.getuuid();
					billmapReq.put("uuid", fileName);
					//insertBill(billmapReq);
					billdao.save(insertBill(billmapReq));
				}
				
				//code for set the bill architecture here 
				
			//create document	
			Document document=new Document();
			// prepare the documnent to pdf and Set the output file for the PDF
			PdfWriter.getInstance(document, new FileOutputStream(CafeConstant.LOCATION+"//"+fileName+".pdf"));
			 // Open the document for writing
            document.open();
			//set the rectangle margin of the document
            document.add(setRectangleMargin(document));
            
            //set the contents of the document
                //set header of the bill and set on center alignment
            Paragraph headline = new Paragraph("cafeManagement Bill",getFont("header"));
            headline.setAlignment(Element.ALIGN_CENTER);
            document.add(headline);
              
                 //set the data in the document
            String data="Name : " +billmapReq.get("name")+"\n" +
            		    "contactNumber : "+billmapReq.get("contactNumber")+"\n"+
            		    "Email : "+billmapReq.get("email")+"\n"+
            		    "paymentMethod : "+billmapReq.get("paymentMethod");
            Paragraph dataContent = new Paragraph(data,getFont("content")); 
            document.add(dataContent);
            
                 //set Table header in the document
               //PdfPTable class allows you to define and format tables in your PDF documents 
            PdfPTable table=new PdfPTable(5);  //set 5 columns of the table
            table.setWidthPercentage(100);
           // table.addCell(addTableHeader(table));
            addTableHeader(table);
              //set tablerow Details in the document.
            JSONArray jsonArray = CafeUtils.getJsonArray(billmapReq.get("productDetails").toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				
				
				JSONObject jsononject =  new JSONObject();
			    jsononject = jsonArray.getJSONObject(i);
				
			    table.addCell(jsononject.get("Name").toString());
				table.addCell(jsononject.get("Catagory").toString());
				table.addCell(jsononject.get("Quantity").toString());
				table.addCell(jsononject.get("Price").toString());
				table.addCell(jsononject.get("Total").toString());

				// document.add(table);
			    
				
				
//				Map<String, Object> rowDetails = CafeUtils.getMapFromJson(jsonArray.getString(i));
//				table.addCell(rowDetails.get("Name").toString());
//				table.addCell(rowDetails.get("Catagory").toString());
//				table.addCell(rowDetails.get("Quantity").toString());
//				table.addCell(rowDetails.get("Price").toString());
//				table.addCell(rowDetails.get("Total").toString());

			}
           
          document.add(table);
			
           //set the footer of the paragraph in the document
           Paragraph footerContent = new Paragraph("Total : "+billmapReq.get("total")+"\n" +"Thank you for Visiting,Please Visit again!!",getFont("content")); 
           document.add(footerContent);
		//Close Document
           document.close();            
           return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}", HttpStatus.OK);
			} else {
				return CafeUtils.getResponseHndler("Data not found", HttpStatus.BAD_REQUEST);}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@SuppressWarnings("unused")
	private void addTableHeader(PdfPTable table) {
		PdfPCell cell=new PdfPCell();
	Stream.of("Name","Catagory","Quantity","Price","Total")
	   .forEach(c->{
		   //PdfPCell cell= new PdfPCell();
		   cell.setBackgroundColor(BaseColor.YELLOW);
		   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		   cell.setPhrase(new Phrase(c));
		   cell.setBorderWidth(2);
		   getFont("tablecell");
		    table.addCell(cell);
           });
	}

	private Boolean validateBill(Map<String, Object> billmapReq) {
		return billmapReq.containsKey("name") && billmapReq.containsKey("contactNumber")
				&& billmapReq.containsKey("email") && billmapReq.containsKey("paymentMethod")
				&& billmapReq.containsKey("productDetails") && billmapReq.containsKey("total");

	}
	
	//private void insertBill(Map<String, Object> billmapReq) {
		private Bill insertBill(Map<String, Object> billmapReq) {
			Bill bill = new Bill();
		try {
			
			bill.setContactNumber((String)billmapReq.get("contactNumber"));
			bill.setUuid((String)billmapReq.get("uuid"));
			bill.setProductDetails((String)billmapReq.get("productDetails"));
			bill.setPaymentMethod((String)billmapReq.get("paymentMethod"));
			bill.setName((String)billmapReq.get("name"));
			bill.setEmail((String)billmapReq.get("email"));
			bill.setCreatedBy(jwtFilter.currentUser());
			bill.setTotal(Integer.parseInt((String)billmapReq.get("total")));
			//billdao.save(bill);
		} catch (Exception e) {
		   e.printStackTrace();
		}
		return bill;  
		
	}
		
//		private void setRectangleMargin(Document doc) throws DocumentException {
//			Rectangle rect = new Rectangle(200,300);
//			rect.enableBorderSide(1);
//			rect.enableBorderSide(2);
//			rect.enableBorderSide(4);
//			rect.enableBorderSide(8);
//			rect.setBorderWidth(2);
//			rect.setBorderColor(BaseColor.BLUE);
//			doc.add(rect);
//		}
		
		private Rectangle setRectangleMargin(Document doc) throws DocumentException {
			//Rectangle rect = new Rectangle(200,300);
			Rectangle rect = new Rectangle(577,825,18,15);
			rect.enableBorderSide(1);
			rect.enableBorderSide(2);
			rect.enableBorderSide(4);
			rect.enableBorderSide(8);
			rect.setBorderWidth(2);
			rect.setBorderColor(BaseColor.BLUE);
			return rect;
		}
		
		private Font getFont(String type) {

			if (type.equalsIgnoreCase("header")) {

				return FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);

			} else if (type.equalsIgnoreCase("content")) {
				 Font datafont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.RED);
				datafont.setStyle(Font.ITALIC);
				return datafont;
			}else if (type.equalsIgnoreCase("tablecell")) {
				Font datafont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 8, BaseColor.GREEN);
				datafont.setStyle(Font.ITALIC);
			}

			return new Font();

		}

		@Override
		public ResponseEntity<?> getBillList() {
			try {
				if(jwtFilter.IsAdmin()) {
					return new ResponseEntity<>(billdao.getAllBillList(),HttpStatus.OK);
				}else {
					return new ResponseEntity<>(billdao.getCurrentUserBilList(jwtFilter.currentUser()),HttpStatus.OK);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.OK);
		}

		@Override
		public ResponseEntity<?> getPdfDownload(Map<String, Object> billmapReq) {
			try {
				if(!billmapReq.containsKey("uuid") && validateBill(billmapReq))
					return CafeUtils.getResponseHndler(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
				String filepath = CafeConstant.LOCATION+"//"+(String)billmapReq.get("uuid")+".pdf";
				       
				      if(CafeUtils.isFileExist(filepath)) {
				    	        //get the bytearray of pdf
				    	  //byte[] b=new byte[0];
				    	  return new ResponseEntity<>(getpdf(filepath),HttpStatus.OK);
				      }else {
				    	  billmapReq.put("isGenerated", false);
				    	  ResponseEntity<?> generatedReport = generatedReport(billmapReq);
				    	      if(generatedReport.getStatusCode()==HttpStatus.OK) {
				    	    	  return new ResponseEntity<>(getpdf(filepath),HttpStatus.OK);
				    	      }else {
				    	    	  return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
				    	      }
				    	             
				      }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		@SuppressWarnings("unused")
		private byte[] getpdf(String filepath) throws IOException {
			 File file=new File(filepath);
			 FileInputStream in=new FileInputStream(file);
			 byte[] pdfBytedata= IOUtils.toByteArray(in);
			return pdfBytedata;
		}

		@Override
		public ResponseEntity<?> deleteBill(String id) {
			try {
				Optional<Bill> findById = billdao.findById(Integer.parseInt(id));
				    if (findById.isPresent()) {
				    	billdao.deleteById(Integer.parseInt(id));
				    	return CafeUtils.getResponseHndler("Bill deleted successfully",HttpStatus.OK);
					}else {
						return CafeUtils.getResponseHndler("Bill Not Found",HttpStatus.PRECONDITION_FAILED);
					}
			} catch (Exception e) {
			   e.printStackTrace();
			}
			return CafeUtils.getResponseHndler(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
		}

}
