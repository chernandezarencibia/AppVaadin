import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';

/**
 * `algo1-a`
 *
 * Algo1A element.
 *
 * @customElement
 * @polymer
 */
class Algo1A extends PolymerElement {

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
        return 'algo1-a';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(Algo1A.is, Algo1A);
