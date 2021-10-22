import '@vaadin/vaadin-icons/vaadin-icons.js';
import '@vaadin/vaadin-date-picker/vaadin-date-picker.js';
import '@vaadin/vaadin-combo-box/vaadin-combo-box.js';
import '@vaadin/vaadin-text-field/vaadin-text-field.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class JournalEditor extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
        display: block;
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

      .edit-form {
        width: 90%;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
      }

      .edit-form .details {
        width: 80%;
        margin: 0 auto;
      }

      .field.notes {
        width: 100%;
      }

      .buttons {
        margin: 3rem auto;
      }

      .buttons button {
        margin-right: 1rem;
      }

      .buttons button:last-child {
        margin-right: 0;
      }

      @media (max-width: 600px) {
        .edit-form {
          width: 90%;
        }

        .edit-form .details {
          width: 100%;
        }
      }

    </style> 
   <header> 
    <h1>New Journal Entry</h1> 
    <vaadin-button on-click="close" class="close-button"> 
     <iron-icon icon="vaadin:close-big"></iron-icon> 
    </vaadin-button> 
   </header> 
   <div class="edit-form"> 
    <div class="details"> 
     <div class="field"> 
      <label>Patient</label> 
      <span>[[patient.lastName]], [[patient.firstName]]</span> 
     </div> 
     <div class="field"> 
      <label for="date">Date</label> 
      <vaadin-date-picker id="date"></vaadin-date-picker> 
     </div> 
     <div class="field"> 
      <label for="appointment">Appointment</label> 
      <vaadin-combo-box id="appointment"></vaadin-combo-box> 
     </div> 
     <div class="field"> 
      <label for="doctor">Doctor</label> 
      <vaadin-combo-box id="doctor"></vaadin-combo-box> 
     </div> 
    </div> 
    <div class="spacer"></div> 
    <div class="field stacked centered notes"> 
     <label for="entry">Notes</label> 
     <vaadin-text-field id="entry" name="entry"></vaadin-text-field> 
    </div> 
    <div class="buttons"> 
     <vaadin-button id="save" class="primary" disabled="[[!valid]]" on-click="save">
      Save
     </vaadin-button> 
     <a class="button" on-click="close">Cancel</a> 
    </div> 
   </div> 
`;
  }

  static get is() { return 'journal-editor' }
}
customElements.define(JournalEditor.is, JournalEditor);

