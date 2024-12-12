// Remove the generalViews and ospViews labels and add the views label to all views. 
// Returns those views that do not have the className defined in their parameters.
// @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}

CALL {
	OPTIONAL MATCH (inventory:inventoryObjects)-[:HAS_VIEW]-(view)
	SET view:views
	RETURN view
}
CALL {
	OPTIONAL MATCH (general:generalViews)
	REMOVE general:generalViews
	SET general:views
	SET general.className = "GeneralView"
	RETURN general
}
CALL {
	OPTIONAL MATCH (osp:ospViews)
	REMOVE osp:ospViews
	SET osp:views
	SET osp.className = "OSPView"
	RETURN osp
}
OPTIONAL MATCH (v:views) WHERE v.className IS NULL
RETURN DISTINCT v