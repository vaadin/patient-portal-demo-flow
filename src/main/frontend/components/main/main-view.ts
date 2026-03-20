import { LitElement, html, css } from 'lit';
import { customElement, property } from 'lit/decorators.js';
import { sharedStyles } from '../shared-styles.js';
import '@vaadin/icon';

@customElement('main-view')
class MainView extends LitElement {
  declare $server: { logout(): void };

  @property({ type: String }) page = '';
  @property({ type: String }) extra = '';

  static get styles() {
    return [sharedStyles, css`
      :host {
        display: flex;
        flex-direction: column;
        position: absolute;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
      }

      nav.menu {
        background: #B5B9BC;
        height: 60px;
        display: flex;
      }

      nav.menu a {
        font-size: 16px;
        line-height: 60px;
        text-transform: uppercase;
        width: 200px;
        text-align: center;
        color: #fff;
        height: 100%;
      }

      nav.menu a:hover {
        color: #ddd;
        background: #747C81;
      }

      nav.menu a.active {
        background: #fff;
        color: #9DD22D;
      }

      nav.menu a.right {
        margin-left: auto;
      }

      .content {
        flex: 1;
        display: flex;
        padding: 16px;
        height: 100%;
      }

      .content > * {
        flex: 1;
      }
    `];
  }

  render() {
    return html`
      <nav class="menu">
        <a router-link href="/patients${this.extra}" class="${this.page === 'patients' ? 'active' : ''}" id="patients">Patients</a>
        <a router-link href="/analytics${this.extra}" class="${this.page === 'analytics' ? 'active' : ''}" id="analytics">Analytics</a>
        <a id="logout" class="right" @click="${() => this.$server.logout()}">
          <vaadin-icon icon="vaadin:exit-o"></vaadin-icon> Logout
        </a>
      </nav>
      <slot></slot>
    `;
  }
}
