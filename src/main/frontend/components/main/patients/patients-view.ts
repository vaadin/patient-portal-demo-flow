import { LitElement, html, css } from 'lit';
import { customElement } from 'lit/decorators.js';
import { sharedStyles } from '../../shared-styles.js';
import '@vaadin/grid/vaadin-grid.js';
import '@vaadin/grid/vaadin-grid-sorter.js';
import '@vaadin/icons/vaadin-icons.js';

@customElement('patients-view')
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

  render() {
    return html`
      <vaadin-grid id="patientsGrid"></vaadin-grid>
      <slot></slot>
    `;
  }
}
