package com.build.devtest;

import java.util.List;
import java.util.ArrayList;

public class ParentViewMapperImpl implements ParentViewMapper {

   private ChildView getChildView(List<ChildView> childViews, String childId) {
      for (ChildView childView : childViews) {
         if (childView.getChildId().equalsIgnoreCase(childId)) {
            return childView;
         }
      }
      return null;
   }

   private ParentView getParentView(List<ParentView> parentViews, String parentId) {
      for (ParentView parentView : parentViews) {
         if (parentView.getParentId().equalsIgnoreCase(parentId)) {
            return parentView;
         }
      }
      return null;
   }

   public List<ParentView> mapRowsToViews(List<ParentRow> parentRows, List<ChildRow> childRows) {

      List<ParentView> parentViews = new ArrayList<ParentView>();
      List<ChildView> childViews = new ArrayList<ChildView>();

      // fill parent list (with null child view lists)
      for (ParentRow parentRow : parentRows) {
         parentViews.add(new ParentView(
            parentRow.getFirstName(), parentRow.getLastName(), parentRow.getAge(),
            parentRow.getParentId(), new ArrayList<ChildView>()
         ));
      }

      // fill child list (and add to parent list)
      for (ChildRow childRow : childRows) {

         // find childView if it was already created
         ChildView childView = getChildView(childViews, childRow.getChildId());

         // if childView was not already created, create it and add to childView list
         if (childView == null) {
            childView = new ChildView(
               childRow.getFirstName(), childRow.getLastName(), childRow.getAge(),
               childRow.getChildId(), null
            );
            childViews.add(childView);
         }
         
         // add to parent's childView list
         ParentView parentView = getParentView(parentViews, childRow.getParentId());
         assert (parentView != null) : "Parent id " + childRow.getParentId() + " not found";
         List<ChildView> parentChildViews = parentView.getChildViews();
         parentChildViews.add(childView);
         parentView.setChildViews(parentChildViews);

      }

      return parentViews;
   }

} 