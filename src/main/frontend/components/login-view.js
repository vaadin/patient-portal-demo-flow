import { LitElement, html, css } from 'lit';
import { sharedStyles } from './shared-styles.js';

class LoginView extends LitElement {
  static get styles() {
    return [sharedStyles, css`
      :host {
        display: block;
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 2rem;
      }

      .login-box {
        width: 400px;
        position: relative;
      }

      .login-box h1 {
        color: #9DD22D;
        text-transform: uppercase;
        font-weight: 300;
      }

      .login-box button {
        width: 40%;
      }

      .alert.error {
        position: absolute;
        bottom: 0;
        transform: translateY(100px);
      }

      button {
        margin-top: 1rem;
      }
    `];
  }

  static get is() { return 'login-view'; }

  render() {
    return html`
      <div class="login-box" @keydown="${e => e.key === 'Enter' && this._login()}">
        <div class="form">
          <h1>Patient portal</h1>
          <div class="field stacked">
            <label for="username">Username</label>
            <input autofocus id="username" type="text" autocomplete="username" value="user">
          </div>
          <div class="field stacked">
            <label for="password">Password</label>
            <input id="password" type="password" autocomplete="password" value="password">
          </div>
          <vaadin-button id="login-button" class="primary" @click="${() => this._login()}">Login</vaadin-button>
        </div>
        <slot></slot>
      </div>
    `;
  }

  _login() {
    const username = this.shadowRoot.querySelector('#username').value;
    const password = this.shadowRoot.querySelector('#password').value;
    this.$server.login(username, password);
  }
}
customElements.define(LoginView.is, LoginView);
