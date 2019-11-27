import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';

/**
 * `prueba-1`
 *
 * Prueba1 element.
 *
 * @customElement
 * @polymer
 */
class Prueba1 extends PolymerElement {

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
        return 'prueba-1';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(Prueba1.is, Prueba1);
