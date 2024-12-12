---
layout: page.11ty.cjs
title: <ol-map> ⌲ Home
---

# &lt;ol-map>

`<ol-map>` is an awesome element. It's a great introduction to building web components with LitElement, with nice documentation site as well.

## As easy as HTML

<section class="columns">
  <div>

`<ol-map>` is just an HTML element. You can it anywhere you can use HTML!

```html
<ol-map></ol-map>
```

  </div>
  <div>

<ol-map></ol-map>

  </div>
</section>

## Configure with attributes

<section class="columns">
  <div>

`<ol-map>` can be configured with attributed in plain HTML.

```html
<ol-map name="HTML"></ol-map>
```

  </div>
  <div>

<ol-map name="HTML"></ol-map>

  </div>
</section>

## Declarative rendering

<section class="columns">
  <div>

`<ol-map>` can be used with declarative rendering libraries like Angular, React, Vue, and lit-html

```js
import {html, render} from 'lit-html';

const name="lit-html";

render(html`
  <h2>This is a &lt;ol-map&gt;</h2>
  <ol-map .name=${name}></ol-map>
`, document.body);
```

  </div>
  <div>

<h2>This is a &lt;ol-map&gt;</h2>
<ol-map name="lit-html"></ol-map>

  </div>
</section>
