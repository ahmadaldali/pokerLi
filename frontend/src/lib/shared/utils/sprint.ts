import type { TranslationFunctions } from "$i18n/i18n-types";
import { sprintsApi } from "../api";
import { redirectTo } from "./redirect";

export const sprintUtils = () => {
  const getSprintTableColumns = (t: TranslationFunctions) => {
    return [
      { key: "name", label: t.tables.sprints.columns.name(), width: "40%" },
      {
        key: "creator",
        label: t.tables.sprints.columns.creator(),
        width: "30%",
      },
      {
        key: "sequence",
        label: t.tables.sprints.columns.sequence(),
        width: "20%",
      },
    ];
  };

  const getJoinedSprintTableActions = (t: TranslationFunctions) => {
    return [
      {
        label: t.tables.sprints.actions.join(),
        onClick: (id: number) => {
          redirectTo(
            t.routes.sprints.details(sprintUtils().encodeSprintId(id)),
          );
        },
      },
    ];
  };

  const getJoinableSprintTableActions = (t: TranslationFunctions) => {
    return [
      {
        label: t.tables.sprints.actions.join(),
        onClick: async (id: number) => {
          let responseForJoin = await sprintsApi().join(id);
          if (responseForJoin.success) {
            redirectTo(
              t.routes.sprints.details(sprintUtils().encodeSprintId(id)),
            );
          }
        },
      },
    ];
  };

  const encodeSprintId = (id: string | number) => {
    return btoa(id.toString());
  };

  const decodeSprintId = (encodedId: string) => {
    return Number(atob(encodedId));
  };

  return {
    getSprintTableColumns,
    getJoinedSprintTableActions,
    encodeSprintId,
    decodeSprintId,
    getJoinableSprintTableActions,
  };
};
