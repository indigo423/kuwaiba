/** 
@license
Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License.
*/

import { html, PolymerElement } from '@polymer/polymer/polymer-element.js';
/**
 * `my-element`
 * my-element
 *
 * @customElement
 * @polymer
 * @demo demo/index.html
 */
class MxGraphCell extends PolymerElement {
  static get template() {
    return html`<style>
         .cell-animated {
           animation:bounce-cell 1s infinite;
        }
         @keyframes bounce-cell {
             0%       { transform: translateY(-3px); }
	     25%, 75% {  transform: translateY(-7px); }
	     50%      {  transform: translateY(-10px); }
	     100%     { transform: translateY(0);}
    }
            </style> <slot></slot>`;
  }

  static get properties() {
    return {
      uuid: {
        type: String,
        value: null
      },
      cell: {           // reference to the cell that represents the Polymer Object MxGraphCell
        type: Object,
        notify: true
      },
       cellSourceLabel: {   // reference to the source cell label
        type: Object,
        notify: true
      },
       cellTargetLabel: {  // reference to the target cell label
        type: Object,
        notify: true
      },

      graph: {          //reference to the graph object container(mxGraph)
        type: Object,
        observer: '_graphChanged'    // listener called when the value is changed
      },
      //array of polymer objects of type mx-graph-cell
      cells: {
        type: Array,
        value: function() { return []; }   
      },
      edge: {           // specify if it is an edge
        type: Boolean,
        value: false
      },
      vertex: {         
        type: Boolean,  // specify if it is a vertex
        value: false
      },
      layer: {         
        type: Boolean,  // specify if it is a vertex
        value: false
      },
      source: {       // uuid of the source vertex
        type: String,
        value: null,
        observer: '_sourceChanged'
      },
      sourceLabel: {
        type: String,
        value: null,
        notify: true
      },
      target: {       // uuid of the target vertex
        type: String,
        value: null,
        observer: '_targetChanged'
      },
      targetLabel: {
        type: String,
        value: null,
        notify: true
      },
      parent: {           //reference to the parent PolymerObject(Mxgraph, MxgraphCell)
        type : Object,
        value : null
      },
      points: {
        type: Array,
        value: function() { return []; },
        notify: true,
        observer: 'fireEdgePointsChanged'   // listener called when the value is changed
      },
      image: {
        type: String,
        value: null,
        observer: '_imageChanged'   // listener called when the value is changed
      },
      label: {
        type: String,
        value: '',
        notify: true,
        observer: 'cellLabelChanged'  // listener called when the value is changed
      },
      width: {
        type: Number,
        value: 80,
        notify: true,
        observer: '_WidthChanged'   // listener called when the value is changed
      },
      height: {
        type: Number,
        value: 30,
         notify: true,   
         observer: '_HeightChanged'   // listener called when the value is changed
      },
      x: {         // position on the x axis.
        type: Number,
        value: 0,
        notify: true,    // notifty for changes in the property
        observer: '_XChanged'   // listener called when the value is changed
      },
      y: {       //position on the y axis.
        type: Number,
        value: 0,
        notify: true,
        observer: '_YChanged'    // listener called when the value is changed
      },
      strokeWidth: {
        type: Number,
        value: 1,
        notify: true
      },
      labelBackgroundColor: {
        type: String,
        value: '#ffffff',
        notify: true,
        observer: '_labelBackgroundColorChanged'  
      },
      perimeterSpacing: {
        type: Number,
        value: '1'
      },
      strokeColor: {
        type: String,
        value: '#000000',
        notify: true
      },
      fontColor : {
        type: String,
        value: '#000000',
        notify: true
      },
      dashed: {
        type: String,
        value: '0',
        notify: true
      },
      curved: {
        type: String,
        value: '0',
        notify: true
      },
      cellParent: {
        type: String,
        value: null,
        notify: true
      },
      cellLayer: {
          type: String,
          value : null
      },
      styleName: { // name of the style in the styleSheet
          type: String,
          value : null,
          observer: 'styleNameChanged' 
      },
      rawStyle: { // intented to assign raw styles without using the styleSheet 
               // example posible value:  'strokeColor=red;shape=ellipse' , 
               // call addRawStyletoCurrent or overrideStlye to asign the style
               
          type: String,
          value : null,
          notify: true
      },
      fillColor: {
        type: String,
        value: null,
        notify: true
      },
      shape: {
          type: String,
          value: 'rectangle',
          notify: true
      },
      verticalLabelPosition: {
          type: String,
          value : 'bottom'
      },
      verticalAlign: {
          type: String,
          value : 'top'
      },
      labelPosition: {
          type: String,
          value : 'bottom'
      },
      movable: {         
        type: String, 
        value: '1',
        observer: 'movableChanged' 
      },
      animateOnSelect: {
          type: Boolean,
          value: false
      },
      fontSize : {
          type: Number,
          value : 10,
        notify: true
      },
      selectable: {
          type: String,
          value: '1',
          observer: 'selectableChanged' 
      },
      edgeStyle: {
          type: Boolean
      },
      /**
       * Specifies whether the cell is collapsed.
       */
      collapsed: {
        type: Boolean,
        value: false,
        observer: '_collapsedChanged'
      },
      /**
       * Specifies whether the cell is visible
       */
      cellVisible: {
        type: Boolean,
        value: true,
        observer: '_cellVisibleChanged'
      },
      /**
       * Specifies whether the cell is connectable
       */
      connectable: {
        type: Boolean,
        value: true,
        observer: '_connectableChanged'
      },
      /**
       * Tooltip for the cell
       */
      tooltip: {
        type: String,
        observer: '_tooltipChanged'
      },
      usePortToConnect: {
         type: Boolean,
         value: false 
      },
      resizable: {
        type: String,
        value: '1'
      },
      editable: {
        type: String,
        value: '1'
      },
      foldable: {
        type: String,
        value: '0'
      },
      autosize: {
        type: String,
        value: '0'
      },
      constituent: {
        type: String,
        value: '0'
      },
      rotation: {
        type: Number,
        value: 0
      },
      tag: {  // Used to filter context menu items
         type: String
      },
      showOverlayButtonsOnSelect: {
          type: Boolean,
          value: false
      },
      overlayButtons : {
          type: Object
      }
    };
  }

  _attachDom(dom) { this.appendChild(dom); }

  constructor() {
    super();
    console.log("Constructor: mxgraphCell")
//    this._pointObserver = new MutationObserver(this.tagAdded.bind(this));
//    this._pointObserver.observe(this, { childList: true});
  }

  ready() {
    super.ready();
    //console.log("Ready: mxgraphCell")
//    console.log("adding Observer")
  }

  initMxGraph() { }

  attached() {
    if (this.cell) {
     // this.cell.setMap(this.map);
    }
              console.log("ATTACHED");

  }
  // called when the graph property changes.
  _graphChanged() {
    console.log("FUNCTION _graphChanged");
    // Marker will be rebuilt, so disconnect existing one from old map and listeners.

    if (this.graph && this.graph instanceof mxGraph) {
      this._graphReady();
    } 
  }
 //  the cell is initialized with the initial parameters
  _graphReady() {
    console.log("FUNCTION _graphReady");
    var t0 = performance.now()
    var t1;
    var parentObject;
    if (this.cellLayer)
        parentObject = this.graph.getModel().getCell(this.cellLayer);
    else if (this.cellParent) {
        parentObject = this.graph.getModel().getCell(this.cellParent);
    } else {
        parentObject = this.graph.getDefaultParent();
    }
    t1 = performance.now()
    console.log("part 1 " + (t1 - t0) + " milliseconds.")
    try {
      if (this.vertex) {  //if the cell is a vertex then create a new one
        console.log("CREATING VERTEX");
//        t0 = performance.now();
        var imageStyle =   this.image ? ';image='.concat(this.image) : '';
        this.cell = this.graph.insertVertex(parentObject, this.uuid ? this.uuid : null, 
                                            this.label, this.x, this.y, this.width, this.height,
              'verticalAlign=' +  this.verticalAlign + 
             ';labelPadding=5' +
             ';shape=' + this.shape +
             ';verticalLabelPosition=' + this.verticalLabelPosition + 
             ';labelPosition=' + this.labelPosition + 
            ';labelBackgroundColor=' + this.labelBackgroundColor +                
            ';fillColor=' + (this.fillColor ? this.fillColor : '#CCC') +                
            ';movable=' + this.movable +   
             ';resizable=' + this.resizable  +    
             ';selectable=' + this.selectable  +    
             ';editable=' + this.editable  +    
              ';strokeColor=' + this.strokeColor  + 
              ';fontSize=' + this.fontSize  + 
            ';fontColor=' + this.fontColor +
            ';rotation=0' + this.rotation +
            ';autosize=' + this.autosize +
            ';constituent=' + this.constituent +
            ';foldable=' + this.foldable
            + imageStyle) ;
//    t1 = performance.now();
//    console.log("part 2 after insert vertex " + (t1 - t0) + " milliseconds.");

      // By default sets the cell tooltip value to null
      this.cell.getTooltip = () => null;
      
      if (this.rawStyle) {
          this.addRawStyleToCurrent();
      } else 
          this.rawStyle = this.cell.style;
      
      if (this.autosize === "1")
          this.graph.updateCellSize(this.cell, true);
    
      if (this.styleName) {
          this.styleNameChanged();
      }
      
      if (this.usePortToConnect) {
         this.addConnectableOverlayPort();
      }
      
      if (this.connectable !== null) {
          this._connectableChanged(this.connectable);
      }
              
      } else if (this.layer) { 
          console.log("CREATINGLAYER");
          var newLayer = new mxCell();
          newLayer.id = this.uuid;
          this.cell = this.graph.getModel().add(this.graph.getModel().getRoot(), newLayer);
      } else if (this.edge) {  //if the cell is an edge then create it.
        console.log("CREATING EDGE");
        if(this.source && this.target) {
                  
          var sourceNode = this.graph.model.getCell(this.source);
          var targetNode = this.graph.model.getCell(this.target);
                
          // create the edge and assign the reference
          this.cell = this.graph.insertEdge(parentObject, this.uuid ? this.uuid : null, this.label, sourceNode, targetNode,
          'fontStyle=1;endArrow=none;orthogonalLoop=1;labelPadding=5'+
            ';perimeterSpacing=' + this.perimeterSpacing +
            ';strokeWidth=' + this.strokeWidth + 
            ';labelBackgroundColor=' + this.labelBackgroundColor +
            ';strokeColor=' + this.strokeColor  +           
            ';dashed=' + this.dashed  +           
            ';curved=' + this.curved  +                             
             ((this.edgeStyle) ? (';edgeStyle=' + this.edgeStyle) : '')  +           
            ';fontColor=' + this.fontColor );

           if (this.rawStyle) {
                this.addRawStyleToCurrent();
            } else 
           this.rawStyle = this.cell.style;
       
            if (this.styleName) {
                this.styleNameChanged();
            }
           // if there are control points, add them to the edge
          if(this.points && this.points.length > 0) {
            var arrayPoints = JSON.parse(this.points); 
            
            var anArray = [];
            arrayPoints.forEach(function(point)  {
              anArray.push(new mxPoint(point.x,point.y));
            },this);
            this.cell.geometry.points = anArray;            
          }    

          var _this = this
 
          // if there are labels, add them to the edge
          if(this.sourceLabel) {
            this.cellSourceLabel = new mxCell(this.sourceLabel, new mxGeometry(-0.8, 0, 0, 0), 
            'resizable=0;editable=1;fontStyle=1;labelPadding=5' +
            ';labelBackgroundColor=' + this.labelBackgroundColor +                
            ';fontColor=' + this.fontColor );
                    
             this.cellSourceLabel.geometry.relative = true;
             this.cellSourceLabel.setConnectable(false);
             this.cellSourceLabel.vertex = true;
             this.cellSourceLabel.id = this.cell.id + "-source";
             this.cell.insert(this.cellSourceLabel);
          }
          if(this.targetLabel) {
            this.cellTargetLabel = new mxCell(this.targetLabel, new mxGeometry(0.8, 0, 0, 0),
             'resizable=0;editable=1;'+
            'fontStyle=1;labelPadding=5' +
            ';labelBackgroundColor=' + this.labelBackgroundColor +                
            ';fontColor=' + this.fontColor);
            this.cellTargetLabel.geometry.relative = true;
	    this.cellTargetLabel.setConnectable(false);
            this.cellTargetLabel.vertex = true;
            this.cellTargetLabel.id = this.cell.id + "-target"
	    this.cell.insert(this.cellTargetLabel);
          }
          
        }
      }
     
    }
    finally {
      // Updates the display
    
    t1 = performance.now()
    console.log("part 2 after insert end update " + (t1 - t0) + " milliseconds.")
    //this.graph.refresh();
    this.fireCellAdded();
    t1 = performance.now()
    console.log("Call to Create vertex took " + (t1 - t0) + " milliseconds.")
    }

  }
  
   // fired when some children tag is added.
  tagAdded(mutations){
      
    console.log("tagAdded Method")

    if (this.graph) {
        console.log("tagAdded Method GRAPH ready")
        this.updateChildren(mutations);
    } else {
        console.log("tagAdded Method NOT GRAPH ready")
        setTimeout(() => {
        
        this.updateChildren(mutations);
       
        }, 2000);  
    }    
  }

// fired when some children tag is added.
  updateChildren(mutations){
    console.log("addPoint Method")
   
    mutations.forEach(function(mutation) {
    console.log("MUTATION TYPE" + mutation.type);

     var addedNodes = mutation.addedNodes;
        addedNodes.forEach(node => {
            if (node) {
                if (node.localName === "mx-graph-cell") {
                console.log("CELL TAG ADDED " + node.uuid);
                        node.parent = this; // add reference to the parent PolymerObject
                        node.graph = this.graph; // add the mxGraph object
                        this.push('cells', node);
                }
                if (node.localName === "mx-graph-point") {
                    node.parent = this;   // add reference to the parent PolymerObject
                    node.cell = this.cell;  // add the mxGraphCell object reference
                    this.push('points', node);             

                  }
            }
        });
        var removedNodes = mutation.removedNodes;
        removedNodes.forEach(node => {
        if (node) {
            if (node.localName === "mx-graph-cell") {
                var index = this.cells.indexOf(node);
                if (index > -1) {
                    this.cells.splice(index, 1);
                }  
                if (node.cell) {
                    console.log("CELL TAG REMOVED " + node.uuid);
                    var nodes = [node.cell];
                    this.graph.removeCells(nodes , false);
                }
            };
          };
        });
     
    }, this); 
     
}

addOverlayButton(buttonId,  label, urlImage, hAlign, vAlign, offsetX, offsetY, width, height)
    {
        if (this.cell) {
	var overlay = new mxCellOverlay(new mxImage(urlImage, width, height), label, hAlign, vAlign);
	overlay.cursor = 'hand';
        overlay.id = buttonId;
        if (!offsetX)
            offsetX = 0;
        if (!offsetY)
            offsetY = 0;
        overlay.offset = new mxPoint(offsetX, offsetY);
	overlay.addListener(mxEvent.CLICK, mxUtils.bind(this, function(sender, evt)
	{
            this.fireClickOverlayButton(buttonId);
	}));
        if (!this.overlayButtons) 
            this.overlayButtons = [];
        this.overlayButtons.push(overlay);
	if (!this.showOverlayButtonsOnSelect)
            this.graph.addCellOverlay(this.cell, overlay);
    }
   };
   
removeOverlayButton(buttonId) {
       if (this.graph) {
           var overlays = this.graph.getCellOverlays(this.cell);
           if (overlays) {
             var theOverlay;
             overlays.forEach(function (overlay) {
             if (overlay.id === buttonId)
                 theOverlay = overlay;
             });
             if (theOverlay) {
                this.graph.removeCellOverlay(this.cell, theOverlay);
             }         
           }
       }
   }
   
removeOverlayButtons() {
       if (this.graph) {         
           this.graph.removeCellOverlays(this.cell);
       }
   }
   
clearCellOverlays() {
    if (this.graph) {
        this.graph.clearCellOverlays(this.cell);
    }
}   

setStyle(key, value) {
     if (this.cell && this.graph && key) {
         mxUtils.setCellStyles(this.graph.model, [this.cell], key, value);
         this.rawStyle = this.cell.style;
     }
 }
    
setChildrenCellPosition(cellId, position) {
    if (this.cell && position >= 0 && this.cell.children.length > position) {
        var theCell;
        this.cell.children.forEach(function (children) {
           if (children.id === cellId)
           theCell = children;
        });
        if (theCell) {   
            var index = this.cell.children.indexOf(theCell);
            this.cell.children.splice(index, 1);
            this.cell.children.splice(position, 0, theCell);
        }
    }
}

setSelfPosition(position) {
    if (this.cell && position >= 0 && this.cell.parent.children.length > position) {        
            var index = this.cell.parent.children.indexOf(this.cell);
            this.cell.parent.children.splice(index, 1);
            this.cell.parent.children.splice(position, 0, this.cell);      
    }
}

updatePosition() {
    this.cell.geometry.x = this.x;
    this.cell.geometry.y = this.y;
    this.graph.refresh();
}

_XChanged() {
    if (this.cell) {
        if (this.cell.geometry.x !== this.x) {
            this.cell.geometry.x = this.x;
            this.graph.refresh();
            console.log("DIF X fired");
        }
        this.fireCellPositionChanged();
    }   
}

_YChanged() {
    if (this.cell) {
        if (this.cell.geometry.y !== this.y) {
            this.cell.geometry.y = this.y;
            this.graph.refresh();
            console.log("DIF Y fired");
        }
        this.fireCellPositionChanged();
    }   
}

_WidthChanged() {
    if (this.cell) {
        if (this.cell.geometry.width !== this.width) {
            this.cell.geometry.width = this.width;
            this.graph.refresh();
            console.log("DIF Width fired");
        }
    }   
}

_HeightChanged() {
    if (this.cell) {
        if (this.cell.geometry.height !== this.height) {
            this.cell.geometry.height = this.height;
            this.graph.refresh();
            console.log("DIF height fired");
        }
    }   
}

_imageChanged() {
    if (this.cell) {
          this.graph.setCellStyles(mxConstants.STYLE_IMAGE, this.image, [this.cell]);
          this.graph.refresh();
    }   
}

toggleVisibility() {
    this.graph.getModel().setVisible(this.cell, !this.graph.getModel().isVisible(this.cell));
}

cellLabelChanged() {
    if (this.graph) {
        this.graph.model.setValue(this.cell, this.label);    
//        this.updateCellSize(true)
        this.fireCellLabelChanged();
    }
}

_labelBackgroundColorChanged() {
    if (this.graph) {
         this.graph.setCellStyles('labelBackgroundColor', this.labelBackgroundColor, [this.cell]);
          this.graph.refresh();
    }
}

updateCellSize(ignoreChildren) {
    this.graph.getModel().beginUpdate();
    try { 
        if (this.graph) {
           this.graph.updateCellSize(this.cell, ignoreChildren);
        }
    }
    finally { 
        this.graph.getModel().endUpdate(); 
    }
 }

// Custom Events

  fireClickCell() {
    this.dispatchEvent(new CustomEvent('click-cell', { detail: { kicked: true } }));
    console.log("click-cell fired");
  }
  
  fireRightClickCell() {
    this.dispatchEvent(new CustomEvent('right-click-cell', { detail: { kicked: true } }));
    console.log("right-click-cell fired");
  }

  fireCellPositionChanged(){
    if (this.cell) {
        this.dispatchEvent(new CustomEvent('cell-position-changed', {detail: {kicked: true}}));
        console.log("cell-position-changed fired");
    }
  }
  
  fireEdgePointsChanged() {
    if (this.cell) {
        this.dispatchEvent(new CustomEvent('edge-points-changed', {detail: {kicked: true}}));
        console.log("edge-points-changed fired");
    }
  }
  
  fireCellLabelChanged() {
    if (this.cell) {
        this.dispatchEvent(new CustomEvent('cell-label-changed', {detail: {kicked: true}}));
        console.log("Cell Label Changed fired");
    }
  }
  
  fireClickOverlayButton(buttonId) {
    this.dispatchEvent(new CustomEvent('click-overlay-button', { detail: { kicked: true, buttonId:buttonId} }));
    console.log("click-overlay-button fired");
  }
  
  fireCellParentChanged(cellId, parentId) {
        this.dispatchEvent(new CustomEvent('cell-parent-changed',
                {detail: {kicked: true, cellId: cellId, parentId: parentId}}));
  }
  
   //This method dispatches a custom event when the graph is loaded
  fireCellAdded() {
        this.dispatchEvent(new CustomEvent('cell-added', {detail: {kicked: true}}));
    }
  
  styleNameChanged() {
      if (this.graph && this.cell) {
          var style = this.graph.getStylesheet().getCellStyle(this.styleName, null);
          if (style) {
              var cs= new Array();
              cs[0] = this.cell;             
              this.graph.setCellStyle(this.styleName, cs);
        }
      }
  }
  /**
   * Adds the styles in rawStyle to the current cell style.
   */
  addRawStyleToCurrent() {
      if (this.graph && this.cell && this.rawStyle) {        
                var keyValuePairs = this.rawStyle.split(";"); 
                var _this = this;
                keyValuePairs.forEach(function(pair)  {
                    var entry = pair.split("="); 
                    if (entry && entry.length === 2)
                        _this.setStyle(entry[0], entry[1]);
                });
      }
  }
  
  movableChanged() {
      if (this.graph) {
         this.graph.setCellStyles(mxConstants.STYLE_MOVABLE, this.movable, [this.cell])
      }
  }
  
  selectableChanged() {
      if (this.graph) {
         this.graph.setCellStyles('selectable', this.selectable, [this.cell])
      }
  }
  
  setMovable(movable) {
      if (this.graph) {
         this.movable = movable;
         this.graph.setCellStyles(mxConstants.STYLE_MOVABLE, movable, [this.cell]);
      }
  }
  
  startAnimation() {
      var state = this.graph.view.getState(this.cell);
      state.shape.node.classList.add('cell-animated');
  }
  
  stopAnimation() {
     var state = this.graph.view.getState(this.cell);
     state.shape.node.classList.remove('cell-animated'); 
  }
  
  selectCell() {
      if (this.graph && this.cell) {
          this.graph.setSelectionCell(this.cell)
      }
  }
  /**
   * Overrides the current cell style by the raw style.
   */
  overrideStyle() {
    if (this.graph && this.cell) {
      this.graph.setCellStyle(this.rawStyle, [this.cell]);
    }
  }
  /**
   * Specifies whether the cell is collapsed
   */
  _collapsedChanged(newValue, oldValue) {
    if (this.graph && this.cell) {
      this.graph.getModel().setCollapsed(this.cell, newValue);
    }
  }
  /**
   * Specifies whether the cell is visible
   */
  _cellVisibleChanged(newValue, oldValue) {
    if (this.graph && this.cell) {
      this.graph.getModel().setVisible(this.cell, newValue);
    }
  }
  /**
   * Specifies wheter the cell is connectable
   */
  _connectableChanged(newValue, oldValue) {
    if (this.graph && this.cell) {
      this.cell.connectable = newValue;
    }
  }
  /**
   * Sets the cell getTooltip function
   */
  _tooltipChanged(newValue, oldValue) {
    if (this.graph && this.cell) {
      this.cell.getTooltip = () => newValue;
    }
  }
  /**
   * Updates the current edge source.
   * @param {string} newValue - The UUID of the new source cell.
   * @param {string} oldValue - The UUID of the old source cell.
   */
  _sourceChanged(newValue, oldValue) {
      if (this.graph && this.cell)
          this._connectCell(this.graph.model.getCell(newValue), true);
  }
  /**
   * Updates the current edge target.
   * @param {string} newValue - The UUID of the new target cell.
   * @param {string} oldValue - The UUID of the old target cell.
   */
  _targetChanged(newValue, oldValue) {
      if (this.graph && this.cell)
          this._connectCell(this.graph.model.getCell(newValue), false);
  }
  /**
   * Connect the specified end of the current edge to the given terminal.
   * @param {MxCell} terminal - Whose terminal should be updated.
   * @param {boolean} source - Indicating if the new terminal is the source or target.
   */
  _connectCell(terminal, source) {
      if (this.graph && this.cell && terminal)
          this.graph.connectCell(this.cell, terminal, source);
  }
  /**
   * Moves the cell to the front or back
   * @param {boolean} back - Specifies if the cell should be moved to back.
   */
  orderCell(back) {
      if (this.graph && this.cell)
          this.graph.orderCells(back, [this.cell]);
  }
  
    addPortToConnect() {
        if (this.graph && this.cell) {
            this.addConnectableOverlayPort();
        }
    }
  
    addConnectableOverlayPort() {
        // Creates a new overlay with an image and a tooltip
        var overlay = new mxCellOverlay(new mxImage('MXGRAPH/images/plug.png', 16, 16), 'Create Link');
        overlay.cursor = 'hand';
        overlay.offset = new mxPoint(12, -8);
        var _this = this;
       
        overlay.addListener('pointerdown', function (sender, eo)
        {
            var evt2 = eo.getProperty('event');
            var state = eo.getProperty('state');

            var pt = mxUtils.convertPoint(_this.graph.container,
                    mxEvent.getClientX(evt2), mxEvent.getClientY(evt2));
            _this.graph.connectionHandler.start(state, pt.x, pt.y);
            _this.graph.isMouseDown = true;
            _this.graph.isMouseTrigger = mxEvent.isMouseEvent(evt2);
            mxEvent.consume(evt2);
        });

        if (!this.overlayButtons) 
            this.overlayButtons = [];
        this.overlayButtons.push(overlay);
	if (!this.showOverlayButtonsOnSelect)
            this.graph.addCellOverlay(this.cell, overlay);
    }
}

window.customElements.define('mx-graph-cell', MxGraphCell);