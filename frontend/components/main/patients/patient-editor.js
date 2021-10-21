import '@vaadin/combo-box/src/vaadin-combo-box.js';
import '@vaadin/date-picker/src/vaadin-date-picker.js';
import '@vaadin/icons/vaadin-icons.js';
import '../../my-icons.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class PatientEditor extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
        display: flex;
        flex-direction: column;
        flex: 1;
        padding-top: 30px;
        overflow-y: scroll;
      }

      header {
        width: 100%;
        display: flex;
        align-items: center;
      }

      header h1 {
        color: #9DD22D;
        text-transform: uppercase;
        display: inline-block;
        margin: 1rem auto;
        border-bottom: 2px solid #9DD22D;
        padding-bottom: 0.25rem;
        font-size: 1.2rem;
      }

      .form-wrapper {
        width: 80%;
        margin: 0 auto;
      }

      footer {
        margin: 3rem auto;
      }

      footer button {
        margin-right: 0.5rem;
      }

      footer button:last-child {
        margin-right: 0;
      }

      @media (max-width: 700px) {
        .form-wrapper {
          width: 90%
        }

        .field label {
          width: 100%;
        }
      }
    </style> 
   <header> 
    <h1>Editing profile</h1> 
    <vaadin-button on-click="close" class="close-button"> 
     <iron-icon icon="vaadin:close-big"></iron-icon> 
    </vaadin-button> 
   </header> 
   <div class="form-wrapper"> 
    <div class="field"> 
     <label>Patient Id</label> 
     <span id="id"></span> 
    </div> 
    <div class="spacer"></div> 
    <div class="field"> 
     <label for="title">Title</label> 
     <vaadin-combo-box id="title"></vaadin-combo-box> 
    </div> 
    <div class="field"> 
     <label for="firstName">First name</label> 
     <vaadin-text-field id="firstName"></vaadin-text-field> 
    </div> 
    <div class="field"> 
     <label for="middleName">Middle name</label> 
     <vaadin-text-field id="middleName"></vaadin-text-field> 
    </div> 
    <div class="field"> 
     <label for="lastName">Last name</label> 
     <vaadin-text-field id="lastName"></vaadin-text-field> 
    </div> 
    <div class="spacer"></div> 
    <div class="field"> 
     <label for="gender">Gender</label> 
     <vaadin-combo-box id="gender"></vaadin-combo-box> 
    </div> 
    <div class="field"> 
     <label for="birthDate">Date of birth</label> 
     <vaadin-date-picker id="birthDate"></vaadin-date-picker> 
    </div> 
    <div class="field"> 
     <label for="ssn">SSN</label> 
     <vaadin-text-field id="ssn"></vaadin-text-field> 
    </div> 
    <div class="spacer"></div> 
    <div class="field"> 
     <label for="doctor">Doctor</label> 
     <vaadin-combo-box id="doctor"></vaadin-combo-box> 
    </div> 
    <footer> 
     <vaadin-button id="save">
      Save
     </vaadin-button> 
     <vaadin-button id="cancel">
      Cancel
     </vaadin-button> 
     <vaadin-button id="delete">
      Delete
     </vaadin-button> 
    </footer> 
   </div> 
   <slot></slot> 
`;
  }

  static get is() { return 'patient-editor' }
}
customElements.define(PatientEditor.is, PatientEditor);

