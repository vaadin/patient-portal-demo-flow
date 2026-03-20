import { LitElement, html, css } from 'lit';
import { sharedStyles } from '../../shared-styles.js';
import '@vaadin/button';
import '@vaadin/icons/vaadin-icons.js';
import '@vaadin/date-picker/vaadin-date-picker.js';
import '@vaadin/combo-box/vaadin-combo-box.js';
import '@vaadin/text-field/vaadin-text-field.js';
import '@vaadin/icon';

class JournalEditor extends LitElement {
  static get styles() {
    return [sharedStyles, css`
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
    `];
  }

  static get is() { return 'journal-editor'; }

  static get properties() {
    return {
      patient: { type: Object }
    };
  }

  constructor() {
    super();
    this.patient = null;
  }

  render() {
    const p = this.patient || {};
    return html`
      <header>
        <h1>New Journal Entry</h1>
        <vaadin-button class="close-button" @click="${() => this.$server.close()}">
          <vaadin-icon icon="vaadin:close-big"></vaadin-icon>
        </vaadin-button>
      </header>
      <div class="edit-form">
        <div class="details">
          <div class="field">
            <label>Patient</label>
            <span>${p.lastName}, ${p.firstName}</span>
          </div>
          <div class="field">
            <label for="date">Date</label>
            <vaadin-date-picker id="date" placeholder="MM/dd/yyyy"></vaadin-date-picker>
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
          <vaadin-button id="save" class="primary" @click="${() => this.$server.save()}">Save</vaadin-button>
          <a class="button" @click="${() => this.$server.close()}">Cancel</a>
        </div>
      </div>
    `;
  }
}
customElements.define(JournalEditor.is, JournalEditor);
