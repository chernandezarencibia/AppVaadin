import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';

/**
 * `a-a`
 *
 * AA element.
 *
 * @customElement
 * @polymer
 */
class AA extends PolymerElement {

    static get template() {
        return html`
            <style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
        `;
    }

    static get is() {
        return 'a-a';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(AA.is, AA);
