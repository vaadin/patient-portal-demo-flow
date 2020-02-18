import '@vaadin/vaadin-grid/vaadin-grid.js';
import '../../my-icons.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class PatientJournal extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
        display: flex;
        flex-direction: column;
      }

      vaadin-grid {
        flex: 1;
      }

      .top {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .details {
        padding: 0 25px 25px 25px;
      }

      .details h3 {
        text-transform: uppercase;
        font-size: 0.8rem;
      }

      .details article {
        white-space: normal;
      }

      .cell-wrapper {
        display: flex;
        flex-wrap: wrap;
      }

      .cell-wrapper div {
        width: 50%;
      }

      .cell-wrapper .date {
        font-weight: bold;
      }

      .cell-wrapper .appointment,
      .cell-wrapper .doctor {
        text-align: right;
      }

      .cell-wrapper .doctor-label {
        text-transform: uppercase;
        font-size: 0.8rem;
        font-weight: bold;
      }
    </style> 
   <iron-media-query query="(max-width: 600px)" query-matches="{{narrow}}"></iron-media-query> 
   <div class="top"> 
    <h2>[[patient.firstName]] [[patient.lastName]]</h2> 
    <a router-link="" class="button primary" href="patients/new-entry/[[patient.id]]"> 
     <iron-icon icon="vaadin:plus"></iron-icon> New entry</a> 
   </div> 
   <slot></slot>  
`;
  }

  static get is() { return 'patient-journal' }

  _expandIcon(expanded) {
    return expanded ? 'chevron-down' : 'chevron-right';
  }

  _toggleExpand(evt) {
    const item = evt.model.item;
    if(this.$.grid.expandedItems && this.$.grid.expandedItems.includes(item)){
      this.$.grid.expandedItems = [];
    } else {
      this.$.grid.expandedItems = [item];
    }
  }
}
customElements.define(PatientJournal.is, PatientJournal);

