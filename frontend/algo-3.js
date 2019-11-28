import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';

/**
 * `algo-3`
 *
 * Algo3 element.
 *
 * @customElement
 * @polymer
 */
class Algo3 extends PolymerElement {

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
        return 'algo-3';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(Algo3.is, Algo3);
