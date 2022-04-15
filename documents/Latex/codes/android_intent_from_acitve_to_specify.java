Intent uniqItem = new Intent(context, UpdateEventActivity.class);
uniqItem.putExtra("id", sendID);
uniqItem.addCategory(Intent.CATEGORY_LAUNCHER);
context.startActivity(uniqItem);