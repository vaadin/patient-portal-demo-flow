import { LitElement, html, css } from 'lit';
import { sharedStyles } from '../../shared-styles.js';
import '@vaadin/grid/vaadin-grid.js';
import '@vaadin/grid/vaadin-grid-sorter.js';
import '@vaadin/icons/vaadin-icons.js';

class PatientsView extends LitElement {
  static get styles() {
    return [sharedStyles, css`
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
    `];
  }

  static get is() { return 'patients-view'; }

  render() {
    return html`
      <vaadin-grid id="patientsGrid"></vaadin-grid>
      <slot></slot>
    `;
  }
}
customElements.define(PatientsView.is, PatientsView);
