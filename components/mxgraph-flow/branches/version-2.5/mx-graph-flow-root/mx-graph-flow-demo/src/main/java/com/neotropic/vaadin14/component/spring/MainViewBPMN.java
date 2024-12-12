package com.neotropic.vaadin14.component.spring;

import com.neotropic.flow.component.mxgraph.bpmn.BPMNDiagram;
import com.neotropic.flow.component.mxgraph.bpmn.SwimlaneNode;
import com.neotropic.flow.component.mxgraph.bpmn.SymbolNode;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;

@Route(value = "bpmn")
public class MainViewBPMN extends VerticalLayout {

    boolean tooglePosition = false;
    public MainViewBPMN(@Autowired MessageBean bean) {
        BPMNDiagram diagram = new BPMNDiagram();
        diagram.setWidth("100%");
        diagram.setHeight("100%");
        add(diagram);       
        
        
        SwimlaneNode swimlaneNode = new SwimlaneNode(diagram);
        swimlaneNode.setUuid("lane1");
        swimlaneNode.setFillColor("#08F0B1");
        swimlaneNode.setWidth(1000);
        SwimlaneNode swimlaneNode2 = new SwimlaneNode(diagram);
        swimlaneNode2.setUuid("lane2");
        swimlaneNode2.setY(300);
        diagram.addNode(swimlaneNode);
        diagram.addNode(swimlaneNode2);
        
        SymbolNode startNode = new SymbolNode(diagram, SymbolNode.SymbolType.Event);
        startNode.setX(50);
        startNode.setY(50);
        startNode.setUuid("nodeEvent");
        startNode.setCellParent("lane1");
        startNode.addCellParentChangedListener(eventListener-> {
           Notification.show("Parent changed to : " + startNode.getCellParent(), 1500 ,Notification.Position.TOP_STRETCH).open();
        });
        diagram.addNode(startNode);
        
        Anchor download = new Anchor();
        download.setId("anchorDownload");
        download.getElement().setAttribute("download", true);
        download.setClassName("hidden");
        download.getElement().setAttribute("visibility", "hidden");
        Button btnDownloadAnchor = new Button();
        btnDownloadAnchor.getElement().setAttribute("visibility", "hidden");
        Button btnDownload = new Button(new Icon(VaadinIcon.DOWNLOAD));
        btnDownload.addClickListener(evt -> {
        
               diagram.updateSVG().then(sc -> {
                   ByteArrayOutputStream jpg_ostream = null;
                   try {
                       Notification.show(sc.asString().length() + "").open();
                       //Workaround to get the right sizes to export
                       int minWidthIndex = sc.asString().indexOf("min-width: ");
                       int minHeightIndex = sc.asString().indexOf("min-height: ");
                       int auxIndexW = sc.asString().substring(minWidthIndex).indexOf("px");
                       int auxIndexH = sc.asString().substring(minHeightIndex).indexOf("px");
                       String width = sc.asString().substring(minWidthIndex + "min-width: ".length(), auxIndexW + minWidthIndex);
                       String height = sc.asString().substring(minHeightIndex + "min-height: ".length(), auxIndexH + minHeightIndex);
                       
                       String parser = XMLResourceDescriptor.getXMLParserClassName();
                       SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
                       
                       ByteArrayInputStream is = new ByteArrayInputStream(sc.asString().getBytes());                    
                       TranscoderInput input_svg_image = new TranscoderInput(is);
                       jpg_ostream = new ByteArrayOutputStream();
                       TranscoderOutput output_jpg_image = new TranscoderOutput(jpg_ostream);
                       JPEGTranscoder my_converter = new JPEGTranscoder();
                        PNGTranscoder t = new PNGTranscoder();
                       my_converter.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,new Float(.9));
                       my_converter.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, new Float(height) + 30);
                       my_converter.addTranscodingHint(JPEGTranscoder.KEY_WIDTH,  new Float(width) + 30);
                       my_converter.transcode(input_svg_image, output_jpg_image);
                       final StreamRegistration regn = VaadinSession.getCurrent().getResourceRegistry().
                               registerResource(createStreamResource("svg.jpg", jpg_ostream.toByteArray()));
                       download.setHref(regn.getResourceUri().getPath());
                       btnDownloadAnchor.clickInClient();
                   } catch (TranscoderException ex) {
                       Logger.getLogger(MainViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
                   } finally {
                       try {
                           jpg_ostream.close();
                       } catch (IOException ex) {
                           Logger.getLogger(MainViewBPMN.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
               });
               
           
        });
        download.add(btnDownloadAnchor);
        HorizontalLayout lytFile = new HorizontalLayout(download, btnDownload);  
        add(lytFile);
    }

     private StreamResource createStreamResource(String name, byte[] ba) {
        return new StreamResource(name, () -> new ByteArrayInputStream(ba));                                
    }

}

