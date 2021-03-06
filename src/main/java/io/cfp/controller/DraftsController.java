/*
 * Copyright (c) 2016 BreizhCamp
 * [http://breizhcamp.org]
 *
 * This file is part of CFP.io.
 *
 * CFP.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package io.cfp.controller;

import io.cfp.domain.exception.CospeakerNotFoundException;
import io.cfp.domain.exception.NotVerifiedException;
import io.cfp.dto.TalkUser;
import io.cfp.entity.Role;
import io.cfp.entity.Talk;
import io.cfp.model.User;
import io.cfp.service.TalkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = { "/v0", "/api" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DraftsController {


    @Autowired
    private TalkUserService talkService;


    /**
     * Add a new draft
     */
    @RequestMapping(value="/drafts", method=RequestMethod.POST)
    @Secured(Role.AUTHENTICATED)
    public TalkUser addDraft(@AuthenticationPrincipal User user, @Valid @RequestBody TalkUser talkUser) throws NotVerifiedException, CospeakerNotFoundException {
        return talkService.addDraft(user.getId(), talkUser);
    }

    /**
     * Get all drafts for current user
     */
    @RequestMapping(value = "/drafts", method = RequestMethod.GET)
    @Secured(Role.AUTHENTICATED)
    public List<TalkUser> listDrafts(@AuthenticationPrincipal User user) throws NotVerifiedException {
        return talkService.findAll(user.getId(), Talk.State.DRAFT);
    }

    /**
     * Get a draft
     */
    @RequestMapping(value = "/drafts/{talkId}", method = RequestMethod.GET)
    @Secured(Role.AUTHENTICATED)
    public TalkUser getDraft(@AuthenticationPrincipal User user, @PathVariable Integer talkId) throws NotVerifiedException {
        return talkService.getOne(user.getId(), talkId);
    }

    /**
     * Delete a draft
     */
    @RequestMapping(value = "/drafts/{talkId}", method = RequestMethod.DELETE)
    @Secured(Role.AUTHENTICATED)
    public TalkUser deleteDraft(@AuthenticationPrincipal User user, @PathVariable Integer talkId) throws NotVerifiedException {
        return talkService.deleteDraft(user.getId(), talkId);
    }

    /**
     * Edit a draft
     */
    @RequestMapping(value= "/drafts/{talkId}", method=RequestMethod.PUT)
    @Secured(Role.AUTHENTICATED)
    public TalkUser editDraft(@AuthenticationPrincipal User user, @Valid @RequestBody TalkUser talkUser, @PathVariable Integer talkId) throws NotVerifiedException, CospeakerNotFoundException {
        talkUser.setId(talkId);
        return talkService.editDraft(user.getId(), talkUser);
    }

}
