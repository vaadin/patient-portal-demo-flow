import '@polymer/polymer/polymer-legacy.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class PatientDetails extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
        display: flex;
        flex-direction: column;
        position: absolute;
        box-sizing: border-box;
        padding: 16px;
        height: 100vh;
        top: 0;
        background: #fff;
        border-left: 5px solid #434D54;
        left: 25%;
        right: 0;
        box-shadow: 2px 2px 10px 8px rgba(0, 0, 0, 0.2);
      }

      nav[hidden].details-nav {
        display: none;
      }

      nav.details-nav {
        display: flex;
        width: 100%;
        justify-content: space-between;
        text-transform: uppercase;
        margin-bottom: 50px;
      }

      nav.details-nav a {
        padding-bottom: 4px;
      }

      nav.details-nav .sub-pages a {
        color: #B5B9BC;
        margin-right: 16px;
      }

      nav.details-nav .sub-pages a.active {
        color: #9DD22D;
        border-bottom: 2px solid #9DD22D;
      }

      nav.details-nav .sub-pages a:last-child {
        margin-right: 0;
      }

      .content {
        flex: 1;
        flex-direction: column;
        display: flex;
        overflow-y: scroll;
      }

      .content > * {
        flex: 1;
      }

      @media (max-width: 600px) {
        nav.details-nav .linktext {
          display: none;
        }
      }
    </style> 
   <nav class="details-nav"> 
    <a router-link="" href="patients"> 
     <iron-icon icon="vaadin:arrow-long-left"></iron-icon> <span class="linktext" id="all-patients">All patients</span></a> 
    <div class="sub-pages"> 
     <a router-link="" href="patients/[[patientId]]" class\$="[[_getItemClasses('profile', page)]]" id="profile">Profile</a> 
     <a router-link="" href="patients/journal/[[patientId]]" class\$="[[_getItemClasses('journal', page)]]" id="journal">Journal</a> 
    </div> 
    <a router-link="" href="patients/edit/[[patientId]]" id="edit">Edit patient</a> 
   </nav> 
   <slot></slot> 
`;
  }

  static get is() { return 'patient-details' }
}
customElements.define(PatientDetails.is, PatientDetails);

