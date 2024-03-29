import '@polymer/polymer/polymer-legacy.js';
const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `<dom-module id="shared-styles"> 
  <template> 
  *,
*:before,
*:after {
    box-sizing: border-box;
}

input[type="date"] {
    font-family: 'Ubuntu', sans-serif;
}

a,
a:visited,
a:active {
    text-decoration: none;
    color: #434D54;
    cursor: pointer;
}

input,
vaadin-text-field,
textarea {
    display: block;
    width: 100%;
    padding: .5rem .75rem;
    font-size: 1rem;
    line-height: 1.25;
    color: #55595c;
    background: #fff none;
    -webkit-background-clip: padding-box;
    background-clip: padding-box;
    border: 1px solid rgba(0, 0, 0, .15);
    border-radius: .25rem;
}

vaadin-text-field {
    border: none;
}

select {
    display: block;
    width: 100%;
    font-size: 1rem;
    color: #55595c;
    background: #fff none;
    border: 1px solid rgba(0, 0, 0, .15);
    border-radius: .25rem;
    height: 2.5rem;
}

input:hover,
select:hover {
    border-color: #8C9297;
}

input:focus,
select:focus {
    outline: #9DD22D auto 5px;
}

input.ng-dirty.ng-invalid,
select.ng-dirty.ng-invalid {
    outline: #F9CB2E auto 5px;
}

button,
a.button {
    background: transparent;
    border: none;
    text-transform: uppercase;
    padding: 0.75rem 1.5rem;
    font-weight: bold;
    color: #434D54;
    border-radius: 0.25rem;
    font-size: 0.9rem;
}

button.primary:focus,
button.primary:hover,
button.primary:active,
a.button.primary:focus,
a.button.primary:hover,
a.button.primary:active {
    background: #727A7F;
    color: #fff;
    outline: none;
}

button:focus,
button:hover,
button:active,
a.button:focus,
a.button:hover,
a.button:active {
    cursor: pointer;
    color: #727A7F;
}

button.primary,
a.button.primary {
    background: #454D53;
    color: #fff;
}

button.danger,
a.button.danger {
    color: #FF5DBA;
}

button.danger:focus,
button.danger:hover,
button.danger:active,
a.button.danger:focus,
a.button.danger:hover,
a.button.danger:active {
    background: #FF5DBA;
    color: #fff;
}

button[disabled],
a.button[disabled] {
    opacity: 0.5;
    cursor: not-allowed;
}

label {
    text-transform: uppercase;
    color: #B3B3B3;
    display: inline-block;
}

.field {
    margin-top: 1rem;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
}

.field.stacked label {
    width: 100%;
    margin-bottom: 0.5rem;
}

.field.centered {
    text-align: center;
}

.field input,
.field select,
.field paper-input,
.field vaadin-combo-box,
.field vaadin-date-picker {
    flex: 1;
    margin-right: 1rem;
}

.field input:last-child,
.field select:last-child {
    margin-right: 0;
}

.field label {
    display: inline-block;
    width: 150px;
    text-transform: uppercase;
}

@media (max-width: 600px) {
    .field label {
        width: 100%;
        margin-bottom: 0.5rem;
    }
}

.alert {
    width: 100%;
    margin-top: 1rem;
    padding: .75rem 1.25rem;
    margin-bottom: 1rem;
    border: 1px solid transparent;
    border-radius: .25rem;
}

.alert.error {
    background-color: #f2dede;
    border-color: #ebcccc;
    color: #a94442;
}

.spacer {
    height: 1.5rem;
}
vaadin-button iron-icon {
    width:24px;
    height:24px
}
  </template> 
 </dom-module>`;

document.head.appendChild($_documentContainer.content);

