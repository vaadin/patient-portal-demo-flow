import '@vaadin/vaadin-grid/vaadin-grid.js';
import '@vaadin/vaadin-grid/vaadin-grid-sorter.js';
import '@vaadin/vaadin-icons/vaadin-icons.js';
import '../../shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class PatientsView extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
       :host {
        display: flex;
        flex-direction: column;
        overflow: hidden;
        height: 100%;
      }

      vaadin-grid {
        flex: 1;
        height: 100%;
      }

      .details-row label {
        width: 50%;
        text-align: right;
        padding-right: 0.5rem;
        margin-bottom: 0;
      }

      @media (max-width: 600px) {
        patient-details.open {
          left: 0;
          box-shadow: none;
          border: none;
        }
      }
    </style> 
   <iron-media-query query="(max-width: 600px)" query-matches="{{narrow}}"></iron-media-query> 
   <vaadin-grid id="patientsGrid">  
   </vaadin-grid> 
   <slot></slot> 
`;
  }

  static get is() { return 'patients-view' }

  static get properties() {
    return {
      currentPatient: {
        type: Object,
        notify: true,
        observer: '_patientChanged'
      },
      currentPatientId: {
        type: String,
        value: "",
        notify: true
      }
    }
  }

  _patientChanged(patient) {
    // Grid fires an initial null selection when initialized which messes up everything
    if (!this.gridInited) {
      this.gridInited = true;
      return;
    }

    this.currentPatientId = patient ? patient.id : "";
    this._selectPatient(patient);
  }

  _selectPatient(patient) {
    this.$.patientsGrid.selectedItems = patient ? [patient] : [];
  }

  _detailsOpen(currentPatient) {
    return currentPatient ? 'open' : '';
  }

  _expandIcon(expanded) {
    return expanded ? 'chevron-down' : 'chevron-right'
  }

  _toggleExpand(evt) {
    const item = evt.model.item;
    if (this.$.patientsGrid.expandedItems && this.$.patientsGrid.expandedItems.includes(item)) {
      this.$.patientsGrid.expandedItems = [];
    } else {
      this.$.patientsGrid.expandedItems = [item];
    }
  }
}
customElements.define(PatientsView.is, PatientsView);

