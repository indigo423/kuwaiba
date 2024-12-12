/*
 * Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>
 *
 * Licensed under the EPL License, Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.neotropic.inventory.modules.ipam.nodes;

import com.neotropic.inventory.modules.ipam.engine.SubnetEngine;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalObjectLight;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.inventory.navigation.navigationtree.nodes.AbstractChildren;

import org.openide.nodes.Node;

/**
 * Children for subnet nodes
 * @author Adrian Martinez Molina {@literal <adrian.martinez@kuwaiba.org>}
 */
public class SubnetChildren extends AbstractChildren {
    
    @Override
    public void addNotify(){
        LocalObjectLight subnet = ((SubnetNode)getNode()).getObject();
        List<LocalObjectLight> ips = CommunicationsStub.getInstance().
                getSubnetUsedIps(subnet.getId(), subnet.getClassName());
        List<LocalObjectLight> subnets = CommunicationsStub.getInstance().
                getSubnetsInSubnet(subnet.getId(), subnet.getClassName());
        
        if (ips == null)
            NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
        
        if (subnets == null)
            NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
        
        if(ips != null && subnets != null){
            List<LocalObjectLight> subnetChildren = new ArrayList<>();
            checkMalformedSubnets(subnets);
            checkMalformedIps(ips);
            
            subnets.sort(new SubnetsComparator());
            subnetChildren.addAll(subnets);

            ips.sort(new IPAddressesComparator());
            subnetChildren.addAll(ips);

            setKeys(subnetChildren);
        }
    }
    
    @Override
    protected void removeNotify() {
        setKeys(Collections.EMPTY_SET);
    } 

    @Override
    protected Node[] createNodes(LocalObjectLight key) {
        
        if(key.getClassName().equals(Constants.CLASS_IP_ADDRESS))
            return new Node[] { new IPAddressNode(key) };
        else
            return new Node[] { new SubnetNode(key) };
    }
    
    /**
     * Checks the malformed IP addresses and show a message with them
     * @param ips a list ip addreses
     */
    private void checkMalformedIps(List<LocalObjectLight> ips){
        List<LocalObjectLight> malformedIps = new ArrayList<>();
        
        for (LocalObjectLight ip : ips) {
            if(!SubnetEngine.isIPv4Address(ip.getName()) && !SubnetEngine.isIPv6Address(ip.getName()))
                malformedIps.add(ip);
        }
        
        if(!malformedIps.isEmpty()){
            LocalObjectLight[] malformedIpAddressesArray = malformedIps.toArray(new LocalObjectLight[0]);
            final JList<LocalObjectLight> lstMalformedSubnets = new JList<>(malformedIpAddressesArray);
            
            final JFrame frame = new JFrame("Malformed IP Addresses");
            frame.setLayout(new BorderLayout());
            frame.setSize(400, 350);
            frame.setLocationRelativeTo(null);
            JLabel lblInstructions = new JLabel("This IP addresses are malformed");
            lblInstructions.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            frame.add(lblInstructions, BorderLayout.NORTH);
            frame.add(lstMalformedSubnets, BorderLayout.CENTER);
            frame.setVisible(true);
            JPanel pnlButtons = new JPanel();
            pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton btnClose = new JButton("Close");
            btnClose.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
            pnlButtons.add(btnClose);
            frame.add(pnlButtons, BorderLayout.SOUTH);
        }
    }
    
    /**
     * Check for malformed the subnets and shows a message with them.
     * @param subnets a list of subnets
     */
    private void checkMalformedSubnets(List<LocalObjectLight> subnets){
        List<LocalObjectLight> malformedSubnets = new ArrayList<>();
        
        for (LocalObjectLight subnet : subnets) {
            if(!isValidIPv4Subnet(subnet.getName()) && !isValidIPv6Subnet(subnet.getName()))
                malformedSubnets.add(subnet);
        }
        
        if(!malformedSubnets.isEmpty()){
            LocalObjectLight[] malformedSubnetsArray = malformedSubnets.toArray(new LocalObjectLight[0]);
            final JList<LocalObjectLight> lstMalformedSubnets = new JList<>(malformedSubnetsArray);

            final JFrame frame = new JFrame("Malformed Subnets");
            frame.setLayout(new BorderLayout());
            frame.setSize(400, 350);
            frame.setLocationRelativeTo(null);
            JLabel lblInstructions = new JLabel("This subnet are malformed");
            lblInstructions.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            frame.add(lblInstructions, BorderLayout.NORTH);
            frame.add(lstMalformedSubnets, BorderLayout.CENTER);
            frame.setVisible(true);
            JPanel pnlButtons = new JPanel();
            pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton btnClose = new JButton("Close");
            btnClose.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
            pnlButtons.add(btnClose);
            frame.add(pnlButtons, BorderLayout.SOUTH);
        }
    }
    
    /**
     * Checks if a given String is a number or not
     * @param str the given String
     * @return true is the string is a number false if not
     */
    private boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
    
    /**
     * Checks if a given String is a hex number or not
     * @param str the given string
     * @return true if is an number in base hex, false if not
     */
    private static boolean isHexNumber (String str) {
        try {
          Long.parseLong(str, 16);
          return true;
        }
        catch (NumberFormatException ex) {
          return false;
        }
    }
    
    /**
     * Checks if a given String is an IPv4 or not
     * @param subnet the given subnet as a String
     * @return true if is an IPv4 false if not
     */
    private boolean isValidIPv4Subnet(String cidrSubnet){
        String subnet = getSubnet(cidrSubnet);
        return SubnetEngine.isIPv4Address(subnet);
    }
    
    /**
     * Checks if a given String is an IPv6 or not
     * @param subnet the given subnet as a String
     * @return true if is an IPv6 false if not
     */
    private boolean isValidIPv6Subnet(String cidrSubnet){
        String subnet = getSubnet(cidrSubnet);
        return SubnetEngine.isIPv6Address(subnet);
    }
    
    /**
     * Splits the subnet from its CDIR format into the single subent
     * @param cidrSubnet an String that contains the subnet in CDIR format subnet/mask
     * @return the subnet without mask
     */
    private String getSubnet(String cidrSubnet){
        String[] cidrSplit = cidrSubnet.split("/");
        if(cidrSplit.length == 2)
            return cidrSplit[0];
        else 
            return ""; //it should not happen
    }
    
    /**
     * Ip address comparator used to sort ip addresses lists
     */
    private class IPAddressesComparator implements Comparator<LocalObjectLight>{
        @Override
        public int compare(LocalObjectLight ipAddrA, LocalObjectLight ipAddrB) {
            String ipAddr1 = ipAddrA.getName();
            String ipAddr2 = ipAddrB.getName();
            
            if(SubnetEngine.isIPv4Address(ipAddr1) && SubnetEngine.isIPv4Address(ipAddr2)){
                ipAddr1 = ipAddr1.replaceAll("\\.", "");
                ipAddr2 = ipAddr2.replaceAll("\\.", "");
               
               return Long.compare(Long.valueOf(ipAddr1), Long.valueOf(ipAddr2));
            }
            else if(SubnetEngine.isIPv6Address(ipAddr1) && SubnetEngine.isIPv6Address(ipAddr2)){
                String[] aIp = SubnetEngine.completeIPv6(ipAddr1);
                String[] bIp = SubnetEngine.completeIPv6(ipAddr2);
                if(Arrays.equals(aIp, bIp))
                    return 0;
              
                for(int k=0; k<8; k++){
                    int x = Integer.parseInt(aIp[k],16);
                    int y = Integer.parseInt(bIp[k],16);
                    int compare = Integer.compare(x, y);
                    if(compare != 0)
                        return compare;
                }
            }
            return 0;    
        }
    }
    
    /**
     * Subnet comparator used to sort subnets lists
     */
    private class SubnetsComparator implements Comparator<LocalObjectLight>{
        @Override//both subnets came in CIDR format
        public int compare(LocalObjectLight subnetA, LocalObjectLight subnetB) {
            String cidrSubnet1 = subnetA.getName();
            String cidrSubnet2 = subnetB.getName();
            if(isValidIPv4Subnet(cidrSubnet1) && isValidIPv4Subnet(cidrSubnet2)){
               String subnet1 = getSubnet(cidrSubnet1).replaceAll("\\.", "");
               String subnet2 = getSubnet(cidrSubnet2).replaceAll("\\.", "");
               
               return Long.compare(Long.valueOf(subnet1), Long.valueOf(subnet2));
            }
            else if(isValidIPv6Subnet(cidrSubnet1) && isValidIPv6Subnet(cidrSubnet2)){
                String[] aSubnetSplit = SubnetEngine.completeIPv6(getSubnet(cidrSubnet1));
                String[] bSubnetSplit = SubnetEngine.completeIPv6(getSubnet(cidrSubnet2));
                if(Arrays.equals(aSubnetSplit, bSubnetSplit))
                    return 0;
                
                for(int k=0; k<8; k++){
                    int x = Integer.parseInt(aSubnetSplit[k],16);
                    int y = Integer.parseInt(bSubnetSplit[k],16);
                    int compare = Integer.compare(x, y);
                    if(compare != 0)
                        return compare;
                }
            }
            return 0;
        }
    }
}
