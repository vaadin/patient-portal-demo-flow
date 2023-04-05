import '@polymer/polymer/polymer-legacy.js';
import '../my-icons.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class MainView extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
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

      .content>* {
        flex: 1;
      }
    </style> 
   <nav class="menu"> 
    <a router-link="" href\$="[[_getPatientsLink(extra)]]" class\$="[[_isActive('patients', page)]]" id="patients">Patients</a> 
    <a router-link="" href\$="[[_getAnalyticsLink(extra)]]" class\$="[[_isActive('analytics', page)]]" id="analytics">Analytics</a> 
    <a id="logout" class="right" on-click="logout"> 
     <iron-icon icon="vaadin:exit-o"></iron-icon> Logout</a> 
   </nav> 
   <slot></slot> 
`;
  }

  static get is() { return 'main-view' }

  static get properties() {
      return {
        extra: {
          type: String,
          value: ""
        }
      }
    }

  _getPatientsLink(extra){
      return "/patients"+extra;
  }

  _getAnalyticsLink(extra){
      return "/analytics"+extra;
  }

  _isActive(link, page) {
    return link === page ? 'active' : '';
  }
}
customElements.define(MainView.is, MainView);

