import { LitElement, html, css } from 'lit';
import { sharedStyles } from '../../shared-styles.js';
import '@vaadin/grid/vaadin-grid.js';
import '@vaadin/icon';

class PatientJournal extends LitElement {
  static get styles() {
    return [sharedStyles, css`
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
    `];
  }

  static get is() { return 'patient-journal'; }

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
      <div class="top">
        <h2>${p.firstName} ${p.lastName}</h2>
        <a router-link class="button primary" href="patients/new-entry/${p.id}" id="new">
          <vaadin-icon icon="vaadin:plus"></vaadin-icon> New entry
        </a>
      </div>
      <slot></slot>
    `;
  }
}
customElements.define(PatientJournal.is, PatientJournal);
